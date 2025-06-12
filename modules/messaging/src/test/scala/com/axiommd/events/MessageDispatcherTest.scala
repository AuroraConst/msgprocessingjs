package com.axiommd.events

import com.axiommd.testutils.*
import zio.json.*

class MessageDispatcherTest extends LaminarWordSpecTesting:
  var msgResult = ""
  //will mutate into json payload from testing of postMessage
  var msgJson = ""
  //transform MessageArg to JSON string

  val msgArg1 = MessageStringArg("testArg1")

  
  "registerHandler" should {
    "add to msgHandlerMap collection" in {
      
      MessageDispatcher.registerHandler(
        msgArg1,
        MessageTypedArgHandler[String]{
          arg => 
            msgResult = s"Handling message with arg: $arg"
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

      //observer for json that is created
      MessageDispatcher.jsonEventStream.foreach{x =>
        msgJson = x
      }
      
      //sends message into MessageDispatcher
      MessageDispatcher.postMessage(msg = msgArg1 )

      msgResult should be(s"Handling message with arg: testArg1")
    }
  }

  "after postMessage" should {
    "produce correct Json representation" in {
      //set up observer for eventStream (foreach). observer dispatches the message for handler to process
      MessageDispatcher.eventStream.foreach{
        msg => MessageDispatcher.dispatchMessage(msg)
      }
      
      //sends message into MessageDispatcher
      MessageDispatcher.postMessage(msg = msgArg1 )

      msgJson.fromJson[MessageArg] match {
        case Left(error) => fail(s"Failed to parse JSON: $error")
        case Right(parsedMsg) => 
          info(s"Parsed json to case class: $parsedMsg")
      }

    }
  }
