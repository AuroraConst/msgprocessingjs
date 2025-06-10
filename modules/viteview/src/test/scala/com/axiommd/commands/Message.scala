package com.axiommd.commands


trait MessageArg:
  lazy val name :String = this.getClass().getSimpleName()
  val arg: Any

trait MessageTypedArg[T] extends MessageArg:
  override val arg: T

trait MessageHandler :
  protected def handleAnyArg(arg:Any): Unit
  def handleMessageArg(msg: MessageArg): Unit =
    handleAnyArg(msg.arg)
    
trait MessageTypedArgHandler[T] extends MessageHandler:
  val f: T => Unit
  override def handleAnyArg(a:Any): Unit =
    f(a.asInstanceOf[T])


