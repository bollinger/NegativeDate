package com.mazebrains.negative.web.home

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.request.mapper.parameter.PageParameters

import org.slf4j._

object HomePage {
  val log = LoggerFactory.getLogger(this.getClass)

}

class HomePage(pp: PageParameters) extends WebPage(pp) {
  import HomePage._

  override def onInitialize(): Unit = {
    super.onInitialize()
    log.info("Home Page onInit")
  }

}