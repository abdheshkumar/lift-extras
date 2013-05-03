package net.liftmodules.extras

import net.liftweb._
import common._
import http._
import http.js._
import JsCmds._
import JE._
import json._
import util.Helpers.tryo

import JsExtras.IIFE

object NgJsCmds {

  private implicit def boxedJValueToJsExp(in: Box[JValue]): JsExp = in.map(jv => new JsExp {
    def toJsCmd = compact(render(jv))
  }).openOr(JsNull)

  /**
    * Creates a module with two services:
    * * ServerParams - params you can pass to your controller
    * * ServerFuncs - functions you can pass to your controller
    */
  case class NgServerModule(name: String, params: JValue, funcs: JsObj) extends JsExp {
    def toJsCmd =
     """|angular.module('%s', [])
        |  .factory('ServerParams', function() {
        |    return %s;
        |  })
        |  .factory('ServerFuncs', function() {
        |    return %s;
        |  });""".format(name, compact(render(params)), funcs.toJsCmd).stripMargin
  }

  /**
    * Call `&#36;scope.&#36;apply` on the passed elementId
    */
  case class NgApply(elementId: String, cmd: JsCmd) extends JsCmd {
    def toJsCmd = WithScopeVar(elementId, Call("scope.$apply", AnonFunc(cmd))).toJsCmd
  }

  /**
    * Call `&#36;scope.&#36;broadcast` on the passed elementId
    */
  case class NgBroadcast(elementId: String, event: String, json: Box[JValue] = Empty) extends JsCmd {
    def toJsCmd = WithScopeVar(elementId, Call("scope.$broadcast", event, json)).toJsCmd
  }

  /**
    * Set a local variable to the scope of an elementId and execute the cmd. All of which is enclosed in an IIFE.
    */
  case class WithScopeVar(elementId: String, cmd: JsCmd) extends JsCmd {
    def toJsCmd = IIFE(
      JsCrVar("scope", Call("angular.element('#%s').scope".format(elementId))) &
      cmd
    ).toJsCmd
  }
}

/**
  * An AngularJs module
  */
/*case class NgModule(name: String) {

  /**
    * Creates a module with two services:
    * * ServerParams - params you can pass to your controller
    * * ServerFuncs - functions you can pass to your controller
    */
  def init(params: JValue, funcs: JsObj): JsCmd = {
    JsRaw(
     """|angular.module('%s', [])
        |  .factory('ServerParams', function() {
        |    return %s;
        |  })
        |  .factory('ServerFuncs', function() {
        |    return %s;
        |  });""".format(name, compact(render(params)), funcs.toJsCmd).stripMargin
    )
  }
}*/

/*case class NgController(elementId: String) {
  private implicit def boxedJValueToJsExp(in: Box[JValue]): JsExp = in.map(jv => new JsExp {
    def toJsCmd = compact(render(jv))
  }).openOr(JsNull)

  def apply(cmd: JsCmd): JsCmd =
    WithScope(Call("scope.$apply", AnonFunc(cmd)))

  def broadcast(event: String, json: Box[JValue] = Empty): JsCmd =
    WithScope(Call("scope.$broadcast", event, json))

  private def WithScope(cmd: JsCmd): JsCmd =
    IIFE(
      JsCrVar("scope", Call("angular.element('#%s').scope".format(elementId))) &
      cmd
    )
}*/
