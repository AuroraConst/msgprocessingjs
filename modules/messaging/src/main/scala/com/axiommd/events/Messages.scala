package com.axiommd.events
import zio.json.*

// Base trait for all message names with a default handler lambda for handling messages of the specific type
// as well as a message naming convention based on the case class name
sealed trait  MessageName [T]:
  def name: String = this.getClass().getSimpleName().stripSuffix("$")
  type  handlerType =  T => Unit
  var defaultHandler:Option[handlerType] = None   //by default the default handler is not set
  def defaultHandler_=(dh:handlerType): Unit =  //this is a setter for the default handler which is typesafe for the specific type of message data payload 
    defaultHandler = Some(dh)


// Marker trait for messages that have a JSON representation
sealed trait MessageJson  extends MessageName[MessageJson]

object MessageJson :
  given JsonCodec[MessageJson] = DeriveJsonCodec.gen[MessageJson]

case class  MessageString(s:String) extends MessageJson 
object MessageString  extends MessageName[MessageString]  //the companion object ensures the corresponsing json codec is applied to MessageString

case class MessageMyData(str: String, i:Int) extends MessageJson
object MessageMyData extends MessageName[MessageMyData] //the companion object ensures the corresponsing json codec is applied to MessageMyData


