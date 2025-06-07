package com.axiommd.events

trait Command 


trait CommandHandler   :
  def handler(c:Command):Unit