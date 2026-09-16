package com.axiommd.events
import zio.json.*


sealed trait  MessageName [T]:
  def name: String = this.getClass().getSimpleName().stripSuffix("$")
  type  handlerType =  T => Unit
  var defaultHandler:Option[handlerType] = None
  def defaultHandler_=(dh:handlerType): Unit =
    defaultHandler = Some(dh)


sealed trait MessageJson  extends MessageName[MessageJson]

object MessageJson :
  given JsonCodec[MessageJson] = DeriveJsonCodec.gen[MessageJson]

case class  MessageString(s:String) extends MessageJson 
object MessageString  extends MessageName[MessageString]  //the companion object ensures the corresponsing json codec is applied to the case class

case class MessageMyData(str: String, i:Int) extends MessageJson
object MessageMyData extends MessageName[MessageMyData] //the companion object ensures the corresponsing json codec is applied to the case class


