package com.axiommd.events
import zio.json.*



sealed trait MessageArg extends Product with Serializable:
  private def derivedMessageName(a:Any) = a.getClass().getSimpleName().stripPrefix("$")
  def name: String = derivedMessageName(this)
  val arg: Any

object MessageArg:
  given JsonCodec[MessageArg] = DeriveJsonCodec.gen[MessageArg]


sealed trait MessageTypedArg[T] extends MessageArg:
  override val arg: T



//all messages are declared from here on
//being under sealed trait allows us to use pattern matching on the type and check for exhaustivity
case class MessageStringArg(arg: String) extends MessageTypedArg[String] 
case class MessageIntArg(arg:Int) extends MessageTypedArg[Int]