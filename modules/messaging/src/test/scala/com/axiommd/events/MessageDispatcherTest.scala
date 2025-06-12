package com.axiommd.events

import com.axiommd.testutils.*
import zio.json.*
import com.raquo.airstream.core.EventStream

class MessageDispatcherTest extends LaminarWordSpecTesting:

  //transform MessageArg to JSON string
  def msgToJson(msg: MessageArg): String = msg.toJson

  // def msgToJson(json:String): MessageArg = json match {
  //   case MessageStringArg.name => MessageStringArg()
  // }

  val jsonEventStream: EventStream[String] = MessageDispatcher.eventStream.map(msgToJson)


  val msgArg1 = MessageStringArg("testArg1")

  
  "registerHandler" should {
    "add to msgHandlerMap collection" in {
      MessageDispatcher.registerHandler(
        msgArg1,
        MessageTypedArgHandler[String]{
          arg => info(s"Handling message with arg: $arg")
        }
      )
      MessageDispatcher.msgHandlerMap should contain key (derivedMessageName(msgArg1))
    }
  }


  "postMessage" should {
    "emit the message to the eventBus" in {
      //set up observer for eventStream (foreach). observer dispatches the message for handler to process
      MessageDispatcher.eventStream.foreach{
        msg => MessageDispatcher.dispatchMessage(msg)
      }

      //observer for json that is created
      jsonEventStream.foreach{x =>
        info(s"json: $x")
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
