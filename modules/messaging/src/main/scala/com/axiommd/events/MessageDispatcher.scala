package com.axiommd.events

import scala.collection.mutable
import com.raquo.laminar.api.L.*
import zio.json.*

object MessageDispatcher:

  /**
    * convertes typed handler to Any  handler
    */
  private def anyHandler[T <: MessageName](typedHandler:T => Unit):(Any) => Unit  = (m:Any) => typedHandler(m.asInstanceOf[T])

  /**
    * map of message name to handler function
    */
  var msgHandlerMap:  mutable.Map[String, (msg:Any ) => Unit] =  mutable.Map.empty

  val eventBus = new EventBus[MessageJson]()
   //streams MessageArgs. allows listeners to be subscribe (foreach etc) or transformations through map, filter etc
  val eventStream = eventBus.events

  //streams messages as JSON string.  attach observers with foreach
  val jsonEventStream: EventStream[String] = eventStream.
    map(_.toJson)


  def registerHandler[T <: MessageName](mn:MessageName, handler:T => Unit): Unit =
    msgHandlerMap += (mn.name ->anyHandler( handler))


  def postMessage(msg: MessageJson): Unit = 
    eventBus.emit(msg)

  def dispatchMessage(msg: MessageJson): Unit =
    //foreach from an option is a convenient way to handle the case where the key might not exist
    msgHandlerMap.get(msg.name).foreach{
      msgHandler => msgHandler(msg)
    }


  