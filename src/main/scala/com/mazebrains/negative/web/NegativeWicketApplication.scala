package com.mazebrains.negative.web


import com.mazebrains.negative.NegativeWebEngineModule
import com.mazebrains.negative.web.home.HomePage
import org.apache.wicket.Application
import org.apache.wicket.DefaultExceptionMapper
import org.apache.wicket.guice.GuiceComponentInjector
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.ws.WebSocketSettings
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage
import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage
import org.apache.wicket.request.IExceptionMapper
import org.apache.wicket.request.IRequestHandler
import org.apache.wicket.request.cycle.IRequestCycleListener
import org.apache.wicket.request.cycle.RequestCycle
import org.slf4s.Logging


object NegativeWicketApplication extends Logging {

  var wicketApplicationKey: String = _

  var contextPath = ""

  def get(): NegativeWicketApplication = {
    Application.get(wicketApplicationKey).asInstanceOf[NegativeWicketApplication]
  }


  /**
    * Broadcast a message to a specific page.
    *
    *
    * @param connectedMsg
    * @param message
    */
  def broadcast(connectedMsg: ConnectedMessage, message: IWebSocketPushMessage): Unit = {
    val application = NegativeWicketApplication.get()

    val webSocketSettings = WebSocketSettings.Holder.get(application)
    val broadcaster = new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry)
    broadcaster.broadcast(connectedMsg, message)
  }

  def broadcast(connectedMsgOpt: Option[ConnectedMessage], message: IWebSocketPushMessage): Unit = {
    connectedMsgOpt.foreach{ cm =>
      broadcast(cm, message)
    }
  }


  def broadcastAll(message: IWebSocketPushMessage): Unit = {
    val application = NegativeWicketApplication.get()

    val webSocketSettings = WebSocketSettings.Holder.get(application)
    val broadcaster = new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry)
    broadcaster.broadcastAll(application, message)
  }


}




/**
  * The entry point for the Greenhouse Wicket Application.
  *
  * Created by peter on 20/07/15.
  */
class NegativeWicketApplication extends WebApplication with Logging {

  override def onDestroy(): Unit = {
    super.onDestroy()
  }

  override def internalDestroy(): Unit = super.internalDestroy()

  override def init(): Unit = {
    super.init()
    NegativeWicketApplication.wicketApplicationKey = getApplicationKey

    getRequestCycleListeners.add( new IRequestCycleListener() {
      override def onBeginRequest(cycle: RequestCycle): Unit = {
        super.onBeginRequest(cycle)
        log.info("My IRequestCycleListener onBegin")
      }

      override def onException(cycle: RequestCycle, ex: Exception): IRequestHandler = {
        log.info("My IRequestCycleListener onException")
        super.onException(cycle, ex)
      }

      override def onEndRequest(cycle: RequestCycle): Unit = {
        super.onEndRequest(cycle)
        log.info("My IRequestCycleListener onEnd")
      }
    }
    )


    getComponentInstantiationListeners().add(new GuiceComponentInjector(this, getGuiceModule()))


    //
    // Mount pages with nice urls.
    //
    mountPage("index.html", getHomePage())
  }




  def getGuiceModule() = new NegativeWebEngineModule()



  /**
    * @see org.apache.wicket.Application#getHomePage()
    */
  override def getHomePage() = classOf[HomePage]



  override def getExceptionMapperProvider() = new DefaultExceptionMapperProvider()


  class DefaultExceptionMapperProvider extends java.util.function.Supplier[IExceptionMapper] {
    def get(): IExceptionMapper = {
      new DefaultExceptionMapper() {

        override def map(e: Exception): IRequestHandler = {
          // enusre the TX is rolled back. when something goes wrong.

          println("*************** ROLL BACK **************************")
          e.printStackTrace()
          println("----------------------------------------------------")

          super.map(e)
        }
      }
    }
  }

}

