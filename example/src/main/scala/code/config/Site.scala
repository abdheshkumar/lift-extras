package code
package config

import code.model._

import net.liftweb._
import common._
import http.S
import sitemap._
import sitemap.Loc._

object MenuGroups {
  val TopBarGroup = LocGroup("topbar")
}

object Site {
  import MenuGroups._

  private def menus = List(
    Menu.i("Home") / "index" >> TopBarGroup,
    Menu.i("Notices") / "notices" >> TopBarGroup submenus(
      Menu.i("Forms Test") / "forms-test",
      Menu.i("Screen Test") / "book-edit"
    ),
    Menu.i("Knockout") / "knockout" >> TopBarGroup submenus(
      Menu.i("Knockout  Example Class") / "knockout-example-cls",
      Menu.i("Knockout  Example Module") / "knockout-example-mod",
      Menu.i("Knockout  Chat") / "chat-knockoutjs"
    ),
    Menu.i("Angular") / "angular" >> TopBarGroup submenus(
      Menu.i("Angular Example") / "angular-example",
      Menu.i("Angule App Demo") / "angdemo" / "index",
      Menu.i("Angule App Demo - Form") / "angdemo" / "partials" / "form" >> Hidden,
      Menu.i("Angule App Demo - Success") / "angdemo" / "partials" / "success" >> Hidden,
      Menu.i("Angule App Demo - Warning") / "angdemo" / "partials" / "warning" >> Hidden
    ),
    // Menu.i("About") / "about" >> TopBarGroup,
    // Menu.i("Contact") / "contact" >> TopBarGroup,
    Menu.i("Error") / "error" >> Hidden,
    Menu.i("404") / "404" >> Hidden,
    Menu.i("Throw") / "throw" >> Hidden >> EarlyResponse(() => throw new Exception("This is only a test."))
  )

  /*
   * Return a SiteMap needed for Lift
   */
  def siteMap: SiteMap = SiteMap(menus:_*)
}
