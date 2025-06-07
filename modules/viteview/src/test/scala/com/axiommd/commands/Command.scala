package com.axiommd.commands

trait Command:
  val title :String 

trait CommandArg[T] :
  val arg: T

trait CommandHandler   :
  def handle(c:Command):Unit