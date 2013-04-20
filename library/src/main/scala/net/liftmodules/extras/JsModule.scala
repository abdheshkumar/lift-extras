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
  * Common functionality for JavaScript modules.
  */
trait JsModuleLike {
  def name: String
  /**
    * JsCmd to init a module
    */
  def init(params: JsExp*): JsCmd =
    Call("%s.init".format(name), params:_*)

  /**
    * Call a function on the JavaScript module.
    */
  def call(func: String, params: JsExp*): Call =
    Call("%s.%s".format(name, func), params:_*)
}

/**
  * A JavaScript module
  */
case class JsModule(name: String) extends JsModuleLike

/**
  * A knockout.js JavaScript module
  */
case class KoModule(name: String, elementId: String) extends JsModuleLike {
  def applyBindings: Call =
    Call("ko.applyBindings", JsVar(name), Call("document.getElementById", elementId))

  override def init(params: JsExp*): JsCmd = {
    super.init(params:_*) & applyBindings
  }
}


/**
  * An AngularJs module
  */
case class NgModule(name: String, elementId: String) {
  /*private def bootstrap: Call =
    Call("angular.bootstrap", Call("document.getElementById", elementId), JsArray(Str(name) :: Nil))*/

  /**
    * Creates a 'server' module with two services:
    * * ServerParams - params you can pass to your controller
    * * ServerFuncs - functions you can pass to your controller
    */
  def init(params: JValue, funcs: JsObj): JsCmd = {
    JsRaw(
     """|angular.module('server', [])
        |  .factory('ServerParams', function() {
        |    return %s;
        |  })
        |  .factory('ServerFuncs', function() {
        |    return %s;
        |  });""".format(compact(render(params)), funcs.toJsCmd).stripMargin
    )
  }

  def apply(cmd: JsCmd, elemId: String = elementId): JsCmd =
    WithScope(Call("scope.$apply", AnonFunc(cmd)), elemId)

  def broadcast(event: String, elemId: String = elementId): JsCmd =
    WithScope(Call("scope.$broadcast", event), elemId)

  private def WithScope(cmd: JsCmd, elemId: String): JsCmd =
    IIFE(
      JsCrVar("scope", Call("angular.element(document.getElementById('%s')).scope".format(elemId))) &
      cmd
    )
}
