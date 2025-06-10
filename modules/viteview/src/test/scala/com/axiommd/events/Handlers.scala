package com.axiommd.events


sealed trait MessageHandler :
  protected def handleAnyArg(arg:Any): Unit
  def handleMessageArg(msg: MessageArg): Unit =
    handleAnyArg(msg.arg)
    
sealed trait MessageTypedArgHandler[T] extends MessageHandler:
  val f: T => Unit
  override def handleAnyArg(a:Any): Unit =
    f(a.asInstanceOf[T])
case class StringHandler(f: String => Unit) extends MessageTypedArgHandler[String] 