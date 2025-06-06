package com.axiommd

import org.scalatest._, Assertions._, funspec.AsyncFunSpec 
import org.scalatest.flatspec._, Assertions.*
import scala.util.Try

import com.raquo.laminar.api.L.{*, given}
import com.raquo.airstream.ownership.OneTimeOwner

import scala.scalajs._
import scala.scalajs.concurrent.JSExecutionContext
import scala.concurrent.{Future,Promise}

import zio.json._
import com.axiom.model.shared.dto.Patient
import com.axiom.model.js.Fetch



/**
 * Trait AsyncFlatSpec is so named because your specification text and tests line up flat 
 * against the left-side indentation level, with no nesting needed. 
 */
class FirstAsyncFlatSpecTest extends AsyncFlatSpec {

  implicit override def executionContext = JSExecutionContext.queue   

  given Owner = new OneTimeOwner(()=>())

  /** 
   * see
   * https://stackoverflow.com/questions/46617946/sleep-inside-future-in-scala-js
   */
  def delay(milliseconds: Int): Future[Unit] = 
    val p = Promise[Unit]()
    js.timers.setTimeout(milliseconds) {
      p.success(())
    }
    p.future


}