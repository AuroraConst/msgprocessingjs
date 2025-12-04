package com.axiommd.events
import zio.json.*
import scala.CanEqual.derived

trait  MessageName :
  def name: String = this.getClass().getSimpleName().stripPrefix("$")
  
case class  MessageString(s:String) extends MessageName

object MessageString extends MessageName:
  given JsonCodec[MessageString] = DeriveJsonCodec.gen[MessageString]

case class MessageMyData(str: String, i:Int) extends MessageName

object MessageMyData extends MessageName :
  given JsonCodec[MessageMyData] = DeriveJsonCodec.gen[MessageMyData]



