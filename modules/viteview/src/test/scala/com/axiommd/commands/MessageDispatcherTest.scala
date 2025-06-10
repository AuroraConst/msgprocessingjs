package com.axiommd.commands

import com.axiommd.testutils.*
import com.axiommd.commands.MessageDispatcher.dispatchMessage

class MessageDispatcherTest extends LaminarWordSpecTesting:
  case class MessageStringArg(arg: String) extends MessageTypedArg[String] 
  case class StringArgHandler(f: String => Unit) extends MessageTypedArgHandler[String] 

  val msgArg1 = MessageStringArg("testArg1")
  val msgArg2 = MessageStringArg("testArg2")

  
  "registerHandler" should {
    "add to msgHandlerMap collection" in {
      MessageDispatcher.registerHandler(
        msgArg1,
        StringArgHandler{
          arg => info(s"Handling message with arg: $arg")
        }
      )
      MessageDispatcher.msgHandlerMap should contain key (MessageStringArg("testArg1").name)
    }
  }


  "postMessage" should {
    "emit the message to the eventBus" in {
      MessageDispatcher.eventStream.foreach{
        msg => dispatchMessage(msg)
      }
      MessageDispatcher.postMessage(msg = msgArg1 )
    }
  }

