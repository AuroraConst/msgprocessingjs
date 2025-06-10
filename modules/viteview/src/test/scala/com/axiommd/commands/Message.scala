package com.axiommd.commands


trait MessageArg:
  lazy val name :String = this.getClass().getSimpleName().dropRight(2)
  val arg: Any

trait MessageTypedArg[T] extends MessageArg:
  override val arg: T

trait MessageHandler   :
  def handle(m:MessageArg) :Unit
    
trait MessageTypedArgHandler[T] extends MessageHandler:
  val f: T => Unit
  def handle(m:MessageTypedArg[T]):Unit =
    f(m.arg)
