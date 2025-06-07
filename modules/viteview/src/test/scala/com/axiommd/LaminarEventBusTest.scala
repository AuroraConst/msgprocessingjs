package com.axiommd


import testutils.*
import com.raquo.laminar.api.L.*

class LaminarEventBusTest extends LaminarWordSpecTesting:
  val nameBus = new EventBus[String]
  val evStream = nameBus.events
  var observedValues = List.empty[String]

  "this" should {
    "work" in {
      evStream.foreach(
        name => 
          observedValues  = name::observedValues
      )

      nameBus.emit("hello")

      observedValues.size should be(1)
      observedValues should contain("hello")
      

    }
  }
