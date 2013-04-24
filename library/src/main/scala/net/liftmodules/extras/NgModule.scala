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

/**
  * An AngularJs module
  */
case class NgModule(name: String) {

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
}

case class NgController(elementId: String) {
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
}
