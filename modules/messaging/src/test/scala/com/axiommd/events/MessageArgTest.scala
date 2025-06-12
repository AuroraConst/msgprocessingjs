package com.axiommd.events


import com.axiommd.testutils.*

class MessageArgTest extends SjsTesting:

  "this" should {
    "work" in {
      val name = MessageStringArg("hello").name
      val name1 = (MessageStringArg("hello")).name
      name should be (name1)
      name should be( "MessageStringArg")
      
    }
  }
