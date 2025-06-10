package com.axiommd.commands
import zio.json.*
import scala.collection.mutable
import com.raquo.laminar.api.L.*

object MessageDispatcher:
  var msgHandlerMap:  mutable.Map[String, MessageHandler] =  mutable.Map.empty
  val eventBus:EventBus[MessageArg] = new EventBus[MessageArg]()

  //allows listeners to be subscribe (foreach etc)
  val eventStream = eventBus.events

  def registerHandler(msg: MessageArg, handler: MessageHandler): Unit =
    msgHandlerMap += (msg.name -> handler)

  def postMessage(msg: MessageArg): Unit = 
    eventBus.emit(msg)

  def dispatchMessage(msg: MessageArg): Unit =
    msgHandlerMap.get(msg.name).foreach{
      msgHandler => msgHandler.handleMessageArg(msg)
    }


  