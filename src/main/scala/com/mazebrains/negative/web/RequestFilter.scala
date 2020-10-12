package com.mazebrains.negative.web

import java.time.LocalDateTime

import com.google.inject.Singleton
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import org.slf4s.Logging


object RequestFilter extends Logging {

  private val requestDateTL = new ThreadLocal[LocalDateTime]()

  def cleanTL(): Unit = {
    requestDateTL.remove()
  }


  def requestDate(): LocalDateTime = {
    val d = requestDateTL.get()
    if (d == null) {
      val date = LocalDateTime.now()
      requestDateTL.set(date)
      date
    } else {
      d
    }
  }
}





@Singleton
class RequestFilter extends Filter with Logging {

  override def init(filterConfig: FilterConfig): Unit = {
    log.info("Request Filter init")
  }

  override def destroy(): Unit = {
    log.info("Request filter destroy")
  }

  override def doFilter(request: ServletRequest, response: ServletResponse,
    chain: FilterChain): Unit = {

    val httpRequest = request.asInstanceOf[HttpServletRequest]

    try {
      chain.doFilter(request, response)
    } catch {
      case rt: RuntimeException =>
        log.error("Got an exception", rt)
    } finally {
      RequestFilter.cleanTL()
    }
  }

}