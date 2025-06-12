package com.axiommd.events


import com.axiommd.testutils.*

class MessageArgTest extends SjsTesting:

  "this" should {
    "work" in {
      val name = MessageStringArg("hello").asInstanceOf[MessageArg].name
      val name1 = derivedMessageName(MessageStringArg("hello"))
      name should be (name1)
      name should be( "MessageStringArg")
      
    }
  }
