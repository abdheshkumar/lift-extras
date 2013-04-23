package code
package snippet

import scala.xml.{NodeSeq, Text}

import net.liftweb.common._
import net.liftweb.http.S
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import net.liftweb.json._
import net.liftweb.util._
import Helpers._
import JsonDSL._

import net.liftmodules.extras._

object AngAppDemo extends SnippetHelper with Loggable {
  implicit val formats = DefaultFormats

  def render(in: NodeSeq): NodeSeq = {
    val ngModule = NgModule("AngDemo", "angular-demo")

    /**
      * A test function that sends a success notice back to the client.
      */
    def sendSuccess(): JsCmd = LiftNotice.success("You have success").asJsCmd

    /**
      * The function to call when submitting the form.
      */
    def saveForm(json: JValue): JsCmd = {
      for {
        msg <- tryo((json \ "textInput").extract[String])
      } yield {
        val logMsg = "textInput from client: "+msg
        logger.info(logMsg)
        S.notice(logMsg)
        ngModule.broadcast("reset-form")
        // ngModule.apply(JsRaw("scope.textInput = ''"))
      }
    }

    val params: JValue = ("x" -> 10)
    val funcs = JsObj(
      "sendSuccess" -> JsExtras.AjaxCallbackAnonFunc(sendSuccess),
      "saveForm" -> JsExtras.JsonCallbackAnonFunc(saveForm)
    )

    val onload = ngModule.init(params, funcs)

    S.appendGlobalJs(JsExtras.IIFE(onload))

    in
  }
}
