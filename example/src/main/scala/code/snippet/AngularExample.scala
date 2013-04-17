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

import net.liftmodules.extras.{LiftNotice, SnippetHelper}

object AngularExample extends SnippetHelper with Loggable {
  implicit val formats = DefaultFormats

  def render(in: NodeSeq): NodeSeq = {
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

        //koModule.call("textInput", Str("")): JsCmd
        Noop
      }
    }

    /**
      * Initialize the knockout view model, passing it the anonymous functions
      */
    val onload: JsCmd = Call("App.views.angular.AngularExample.init", 50)
    /*koModule.init(
      JsExtras.AjaxCallbackAnonFunc(sendSuccess),
      JsExtras.JsonCallbackAnonFunc(saveForm)
    )*/

    S.appendJs(onload)

    in
  }
}
