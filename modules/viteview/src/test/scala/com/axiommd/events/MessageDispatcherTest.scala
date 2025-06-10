package com.axiommd.events

import com.axiommd.testutils.*
import zio.json.*
import com.raquo.airstream.core.EventStream

class MessageDispatcherTest extends LaminarWordSpecTesting:

  //transform MessageArg to JSON string
  def msgToJson(msg: MessageArg): String = msg match {
    case m:MessageStringArg => m.toJson
    case default => 
      info(s"Unhandled message type: ${default.getClass.getSimpleName}")
      "error"
  }

  val jsonEventStream: EventStream[String] = MessageDispatcher.eventStream.map(msgToJson)


  val msgArg1 = MessageStringArg("testArg1")

  
  "registerHandler" should {
    "add to msgHandlerMap collection" in {
      MessageDispatcher.registerHandler(
        msgArg1,
        StringHandler{
          arg => info(s"Handling message with arg: $arg")
        }
      )
      MessageDispatcher.msgHandlerMap should contain key (msgArg1.name)
    }
  }


  "postMessage" should {
    "emit the message to the eventBus" in {
      //set up observer for eventStream (foreach). observer dispatches the message for handler to process
      MessageDispatcher.eventStream.foreach{
        msg => MessageDispatcher.dispatchMessage(msg)
      }
      
      //sends message into MessageDispatcher
      MessageDispatcher.postMessage(msg = msgArg1 )
    }
  }

  "json EventStream" should {
    "stream json that came from converted case classes" in {
      //set up observer for eventStream (foreach). observer dispatches the message for handler to process
      MessageDispatcher.eventStream.foreach{
        msg => MessageDispatcher.dispatchMessage(msg)
      }
      
      //sends message into MessageDispatcher
      MessageDispatcher.postMessage(msg = msgArg1 )
    }
  }
