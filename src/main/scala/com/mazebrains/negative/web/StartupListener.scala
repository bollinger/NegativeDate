package com.mazebrains.negative.web

import java.time.LocalDateTime
import java.util.Date
import java.util.TimeZone

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener



object StartupListener {
  var startTime: Date = _
}


class StartupListener  extends ServletContextListener {


  override def contextInitialized(event: ServletContextEvent): Unit = {
    // thanks to the figlet command line tool.
    println("""
              | _   _                  _   _
              || \ | | ___  __ _  __ _| |_(_)_   _____
              ||  \| |/ _ \/ _` |/ _` | __| \ \ / / _ \
              || |\  |  __/ (_| | (_| | |_| |\ V /  __/
              ||_| \_|\___|\__, |\__,_|\__|_| \_/ \___|
              |            |___/
              |
              |     """.stripMargin)
    println(s"Server Startup ${LocalDateTime.now().toString}")

    StartupListener.startTime = new Date()

    // see if this fixes funky time offsets in DATE columns from DB.
    // otherwise hibernate uses the default time zone which could be anything.
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
  }

  override def contextDestroyed(event: ServletContextEvent): Unit = {
  }

}

