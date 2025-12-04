package com.axiommd.events

import com.axiommd.testutils.*

class MessageDispatcherTest extends LaminarWordSpecTesting :
  
  
  before {
    info("Setting up MessageDispatcherTest!!!!!!!!!!!!!!!!!!!!!")
    MessageDispatcher.msgHandlerMap.clear()
    msgResult = ""
    msgJson =  ""
  }


  var msgResult = ""
  //will mutate into json payload from testing of postMessage
  var msgJson = ""
  //transform MessageArg to JSON string

  def messageStringHandler(ms:MessageString): Unit =
    s"Handling message with arg: ${ms.s}"
    msgResult = ms.s

  val msgArg1 = MessageString("testArg1")

  
  "registerHandler" should {
    "add to msgHandlerMap collection" in {
      
      MessageDispatcher.registerHandler(
        MessageString,
        messageStringHandler
      )
      
      MessageDispatcher.msgHandlerMap should contain key (msgArg1.name)
    }
  }

  "registerDefaultHandler" should {
    "add to Message with DefaultHandler to collection" in {
      MessageDispatcher.msgHandlerMap shouldNot contain key (msgArg1.name)
      
      val a:MessageString.handlerType = (ms:MessageString) => messageStringHandler(ms)
      MessageDispatcher.registerHandler(
        MessageString,
        a
      )

      
      MessageDispatcher.msgHandlerMap should contain key (MessageString.name)
    }
  }


  "postMessage" should {
    "emit the message to the eventBus" in {
      val handler:MessageString.handlerType = (ms:MessageString) => messageStringHandler(ms)
      MessageDispatcher.registerHandler(
        MessageString, handler
      )
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

      msgResult should be("testArg1")
    }
  }

  "after postMessage" should {
    "produce correct Json representation" in {
      pending
      //set up observer for eventStream (foreach). observer dispatches the message for handler to process
      // MessageDispatcher.eventStream.foreach{
      //   msg => MessageDispatcher.dispatchMessage(msg)
      // }
      
      //sends message into MessageDispatcher
      // MessageDispatcher.postMessage(msg = msgArg1 )

      // msgJson.fromJson[MessageArg] match {
      //   case Left(error) => fail(s"Failed to parse JSON: $error")
      //   case Right(parsedMsg) => 
      //     info(s"Parsed json to case class: $parsedMsg")
      // }
      

    }
  }
