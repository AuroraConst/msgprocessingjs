package com.axiommd.events
import zio.json.*



sealed trait MessageArg extends Product with Serializable:
  val name:String
  val arg: Any
object MessageArg:
  given JsonCodec[MessageArg] = DeriveJsonCodec.gen[MessageArg]


sealed trait MessageTypedArg[T] extends MessageArg:
  override val arg: T


private def derivedMessageName(a:Any) = a.getClass().getSimpleName().dropRight(1)
// object MessageTypedArg :
//   given [T : JsonCodec]  : JsonCodec[MessageTypedArg[T]] =
//     DeriveJsonCodec.gen[MessageTypedArg[T]]   //although this is possible, because Messages will be
    //stored in a collection, the type information of the Arg gets lost



//all messages are declared from here on
//being under sealed trait allows us to use pattern matching on the type and check for exhaustivity
case class MessageStringArg(name:String,arg: String) extends MessageTypedArg[String] 
object MessageStringArg :
  lazy val name = derivedMessageName(this)
  def apply(arg:String):MessageStringArg = MessageStringArg(derivedMessageName(this),arg)
  given JsonCodec[MessageStringArg] = DeriveJsonCodec.gen[MessageStringArg]
//   given JsonEncoder[MessageStringArg] = DeriveJsonEncoder.gen[MessageStringArg]
//   given JsonDecoder[MessageStringArg] = DeriveJsonDecoder.gen[MessageStringArg]



case class MessageIntArg(name:String,arg:Int) extends MessageTypedArg[Int]
object MessageIntArg :
  lazy val name = derivedMessageName(this)
  def apply(arg:String):MessageStringArg = MessageStringArg( derivedMessageName(this),arg)
  given JsonCodec[MessageIntArg] = DeriveJsonCodec.gen[MessageIntArg]
