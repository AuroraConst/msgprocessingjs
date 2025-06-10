package com.axiommd.commands


import com.axiommd.testutils.*
import typings.moment.momentStrings.s

class MessageTest extends SjsTesting:

  "Message.name" should {
    "standardize naming like this" in {

      case class MessageStringArg(arg: String) extends MessageTypedArg[String] 
      val name = MessageStringArg("test").name

      name shouldBe ("MessageStringArg")
      

    }
  }
