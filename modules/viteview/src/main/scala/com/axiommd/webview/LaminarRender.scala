package com.axiommd.webview


import org.scalajs.dom
import com.raquo.laminar.api.L.{*}

object LaminarRender :
  def consoleOut(msg: String): Unit = {
    dom.console.log(s"%c $msg","background: #222; color: #bada55")
  }

  def apply():Element = 

    import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits.global
    ModelFetch.fetchPatients.foreach{ p => 
      consoleOut(s"Patient: $p")
    }
    div("hello world!!!!!!!!!!!!!!!!!!")    