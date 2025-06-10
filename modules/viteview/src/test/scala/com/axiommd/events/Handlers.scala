package com.axiommd.events


trait MessageHandler :
  protected def handleAnyArg(arg:Any): Unit
  def handleMessageArg(msg: MessageArg): Unit =
    handleAnyArg(msg.arg)
    
trait MessageTypedArgHandler[T] extends MessageHandler:
  val f: T => Unit
  override def handleAnyArg(a:Any): Unit =
    f(a.asInstanceOf[T])

//all handler implementations are declared from here on
case class StringHandler(f: String => Unit) extends MessageTypedArgHandler[String] 