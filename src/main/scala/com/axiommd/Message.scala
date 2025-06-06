package com.axiommd
import zio.json._

case class Message(typ:String, value:String)




object Message{
  implicit val decoder: JsonDecoder[Message] = DeriveJsonDecoder.gen[Message]
  implicit val encoder: JsonEncoder[Message] = DeriveJsonEncoder.gen[Message]
}

