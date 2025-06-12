package com.axiommd.events

import scala.collection.mutable
import com.raquo.laminar.api.L.*
import zio.json.*

object MessageDispatcher:
  var msgHandlerMap:  mutable.Map[String, MessageHandler] =  mutable.Map.empty
  val eventBus:EventBus[MessageArg] = new EventBus[MessageArg]()
   //streams MessageArgs. allows listeners to be subscribe (foreach etc) or transformations through map, filter etc
  val eventStream = eventBus.events

  //streams messages as JSON string.  attach observers with foreach
  val jsonEventStream: EventStream[String] = eventStream.map(_.toJson)

  def registerHandler(msg: MessageArg, handler: MessageHandler): Unit =
    msgHandlerMap += (msg.name -> handler)

  def postMessage(msg: MessageArg): Unit = 
    eventBus.emit(msg)

  def dispatchMessage(msg: MessageArg): Unit =
    //foreach from an option is a convenient way to handle the case where the key might not exist
    msgHandlerMap.get(msg.name).foreach{
      msgHandler => msgHandler.handleMessageArg(msg)
    }


  