package com.mazebrains.negative

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject._
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule
import com.mazebrains.negative.web.NegativeWicketApplication
import com.mazebrains.negative.web.RequestFilter
import javax.servlet.http.HttpServlet
import org.apache.wicket.protocol.http.WicketFilter


class NegativeGuiceServletConfig extends GuiceServletContextListener {
  override def getInjector(): Injector = {
    try {
      Guice.createInjector(new NegativeModule())

    } catch {
      case rt: RuntimeException =>
        rt.printStackTrace()
        throw rt
    }
  }
}



class NegativeWebEngineModule extends AbstractModule {
  override def configure(): Unit = {
  }
}


class NegativeModule extends ServletModule {

  def serve(loc: String, c: Class[_ <: HttpServlet]): Unit = {
    serve(loc).`with`(c)
  }


  override def configureServlets(): Unit = {
    super.configureServlets()

    install(new NegativeWebEngineModule)


    filter("/*").through(classOf[RequestFilter])

    val wicketApplicationPath = "/*"
    val wicketFilterName = "wicket.websocket"

    val wicketParams = new java.util.HashMap[String, String]()

    val wicketApplicationClassName = classOf[NegativeWicketApplication].getName
    wicketParams.put("applicationClassName", wicketApplicationClassName)
    wicketParams.put("configuration", "deployment")
    wicketParams.put("filterName", wicketFilterName)
    wicketParams.put(WicketFilter.FILTER_MAPPING_PARAM, wicketApplicationPath)  // ContextParamWebApplicationFactory.

    val wicketFilterClass = classOf[org.apache.wicket.protocol.ws.javax.JavaxWebSocketFilter]  // for tomcat 8 and wicket 7

    bind(wicketFilterClass).in(classOf[Singleton])
    val f = filter(wicketApplicationPath).through(wicketFilterClass, wicketParams)
  }
}