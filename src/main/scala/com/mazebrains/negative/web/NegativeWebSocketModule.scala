package com.mazebrains.negative.web

import com.google.inject.AbstractModule
import com.mazebrains.negative.NegativeWebEngineModule


/**
  * A guice module for use in web socket requests.
  *
  */
class NegativeWebSocketModule(/*session: HttpSession*/) extends AbstractModule {

  override def configure(): Unit = {
    this.install(new NegativeWebEngineModule())
  }
}