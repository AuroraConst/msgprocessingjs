package com.axiommd.commands


import com.axiommd.testutils.*

class MessageTest extends SjsTesting:
  
  val instance = MessageStringArg("testArg1")
  val name = instance.name

  "Message.name" should {

    "standardize naming like this" in {
      name shouldBe ("MessageStringArg")
    }
    "argument value should be accessible" in {
      instance.arg shouldBe "testArg1"
    }
  }


  "MessageTypedArgHandler" should {

    val handler =StringHandler(arg => info(s"Handling message with arg: $arg correctly"))

    "handle a message with a string argument" in {
      handler.handleMessageArg(instance)
    }
  }
