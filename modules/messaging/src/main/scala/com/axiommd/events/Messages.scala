package com.axiommd.events
import zio.json.*

trait DefaultHandler[T <: MessageName] :
  type handlerType = T => Unit

sealed trait  MessageName :
  def name: String = this.getClass().getSimpleName().stripSuffix("$")

sealed trait MessageJson  extends MessageName

object MessageJson :
  given JsonCodec[MessageJson] = DeriveJsonCodec.gen[MessageJson]

case class  MessageString(s:String) extends MessageJson 
object MessageString  extends MessageName with DefaultHandler[MessageString] 

case class MessageMyData(str: String, i:Int) extends MessageJson
object MessageMyData extends MessageName  with DefaultHandler[MessageMyData] 


