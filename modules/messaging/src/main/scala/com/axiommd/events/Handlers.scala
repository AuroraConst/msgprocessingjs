package com.axiommd.events


sealed trait MessageHandler :
  protected def handleAnyArg(arg:Any): Unit
  def handleMessageArg(msg: MessageArg): Unit =
    handleAnyArg(msg.arg)
    
case class MessageTypedArgHandler[T](f: T =>Unit) extends MessageHandler:
  override def handleAnyArg(a:Any): Unit =
    f(a.asInstanceOf[T])
