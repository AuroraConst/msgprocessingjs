package com.axiommd.webview

import com.raquo.laminar.api.L.{*}
import org.scalajs.dom
// import vendor.highlightjs.hljs


object Main :
  def consoleOut(msg: String): Unit = {
     dom.console.log(s"%c $msg","background: #222; color: #bada55")
  }
  @main def entrypoint(): Unit = 

    consoleOut ("Hello, world from console!!!!???")
    println("Hello, world!")
    // Scala.js outputs to the browser dev console, not the sbt session
    // Always have the browser dev console open when developing web UIs.
    println("-- Scala.js app start --")


    val element = dom.document.querySelector("#app")
    ModelFetch.fetchPatients
    renderOnDomContentLoaded(element,LaminarRender())
