package com.axiommd.events
import zio.json.*
import scala.CanEqual.derived

sealed trait  MessageName :
  def name: String = this.getClass().getSimpleName().stripPrefix("$")

sealed trait MessageJson  extends MessageName

object MessageJson :
  given JsonCodec[MessageJson] = DeriveJsonCodec.gen[MessageJson]


  
case class  MessageString(s:String) extends MessageJson

object MessageString  extends MessageName
  // given JsonCodec[MessageString] = DeriveJsonCodec.gen[MessageString]

case class MessageMyData(str: String, i:Int) extends MessageJson
object MessageMyData extends MessageName 


