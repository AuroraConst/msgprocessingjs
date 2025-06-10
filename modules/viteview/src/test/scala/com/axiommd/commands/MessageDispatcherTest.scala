package com.axiommd.commands


import com.axiommd.testutils.*

class MessageDispatcherTest extends LaminarWordSpecTesting:
  var observedValues = List.empty[String]

  "Message.name" should {
    "standardize naming like this" in {

      case class MessageStringArg(arg: String) extends MessageTypedArg[String] 

      MessageStringArg("test").name shouldBe ("MessageStringArg")

    }
  }
