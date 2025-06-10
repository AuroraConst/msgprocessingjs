package com.axiommd.commands
import zio.json.*

sealed trait MessageArg:
  lazy val name :String = this.getClass().getSimpleName()
  val arg: Any

sealed trait MessageTypedArg[T] extends MessageArg:
  override val arg: T


case class MessageStringArg(arg: String) extends MessageTypedArg[String] 
object MessageStringArg {
  given JsonEncoder[MessageStringArg] = DeriveJsonEncoder.gen[MessageStringArg]
  given JsonDecoder[MessageStringArg] = DeriveJsonDecoder.gen[MessageStringArg]
}

