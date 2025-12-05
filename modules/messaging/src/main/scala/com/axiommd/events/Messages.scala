package com.axiommd.events
import zio.json.*


sealed trait  MessageName [T]:
  def name: String = this.getClass().getSimpleName().stripSuffix("$")
   type  handlerType =  T => Unit

sealed trait MessageJson  extends MessageName[MessageJson]

object MessageJson :
  given JsonCodec[MessageJson] = DeriveJsonCodec.gen[MessageJson]

case class  MessageString(s:String) extends MessageJson 
object MessageString  extends MessageName[MessageString]

case class MessageMyData(str: String, i:Int) extends MessageJson
object MessageMyData extends MessageName[MessageMyData] 


