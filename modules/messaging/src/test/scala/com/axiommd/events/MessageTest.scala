package com.axiommd.events


import com.axiommd.testutils.*

class MessageTest extends SjsTesting:
  
  val instance = MessageString("testArg1")
  val name = instance.name

  "Message.name" should {

    "standardize naming like this" in {
      name shouldBe ("MessageString")
    }
    "argument value should be accessible" in {
      instance.s shouldBe "testArg1"
    }
  }


  "handler" should {


    "handle a message with a string argument" in {
      pending
     }
  }
