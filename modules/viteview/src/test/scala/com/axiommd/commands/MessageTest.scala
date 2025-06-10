package com.axiommd.commands


import com.axiommd.testutils.*
import typings.moment.momentStrings.s

class MessageTest extends SjsTesting:
  case class MessageStringArg(arg: String) extends MessageTypedArg[String] 
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
    case class MessageStringArgHandler(f: String => Unit) extends MessageTypedArgHandler[String] 

    val handler = MessageStringArgHandler(arg => info(s"Handling message with arg: $arg correctly"))

    "handle a message with a string argument" in {
      handler.handleMessageArg(instance)
    }
  }
