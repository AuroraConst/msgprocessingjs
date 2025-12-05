package com.axiommd.events

import com.axiommd.testutils.*

class MessageDispatcherTest extends LaminarWordSpecTesting :
  
  
  before {
    MessageDispatcher.msgHandlerMap.clear()
    msgResult = ""
  }


  var msgResult = ""
  //will mutate into json payload from testing of postMessage
  var msgJson = ""
  //transform MessageArg to JSON string

  val msgArg1 = MessageString("testArg1")

  
  "registerHandler" should {
    "add to msgHandlerMap collection" in {
      def messageStringHandler: MessageString.handlerType = (ms:MessageString) =>
        s"Handling message with arg: ${ms.s}"
        msgResult = ms.s
      
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

      def messageStringHandler: MessageString.handlerType = (ms:MessageString) =>
        s"Handling message with arg: ${ms.s}"
        msgResult = ms.s
      
      val handler:MessageString.handlerType = (ms:MessageString) => messageStringHandler(ms)
      MessageDispatcher.registerHandler(
        MessageString,
        handler
      )

      
      MessageDispatcher.msgHandlerMap should contain key (MessageString.name)
    }
  }



  "postMessage" should {
    "emit the message to the eventBus" in {
      val handler1:MessageMyData.handlerType = (ms:MessageMyData) => {msgResult = s"$ms";info(s"$ms");}
      val handler2:MessageString.handlerType = (ms:MessageString) => {msgResult = s"$ms";info(s"$ms");}
      
      MessageDispatcher.registerHandler[MessageMyData](
        MessageMyData, handler1
      )
      MessageDispatcher.registerHandler[MessageString](
        MessageString, handler2
      )



      
      //sends message into MessageDispatcher
      val msg1 = MessageMyData("hello", 42)
      val msg2 = MessageString("testStringMessage")
      MessageDispatcher.postMessage(msg1) 
      msgResult should be(s"$msg1")

      MessageDispatcher.postMessage(msg2)
      msgResult should be(s"$msg2")

      MessageDispatcher.postMessage(msg1) 
      msgResult should be(s"$msg1")

      MessageDispatcher.postMessage(msg2)
      msgResult should be(s"$msg2")
      

    }
  }

