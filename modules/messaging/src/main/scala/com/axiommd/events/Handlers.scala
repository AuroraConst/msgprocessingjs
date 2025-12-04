package com.axiommd.events

import scala.collection.mutable
//TODO rough 

val myStringHandler: MessageString => Unit  = (m:MessageString) => println(s"Handling String message with s: ${m.s}")
def myDataHandler(m:MessageMyData) : Unit = println(s"Handling MyData message with str: ${m.str} and i: ${m.i}")
def anyHandler[T <: MessageName](th:T => Unit):(Any) => Unit  = m => ()



val mMap:mutable.Map[String, (msg:Any ) => Unit] = mutable.Map(MessageString.name -> anyHandler(myStringHandler))


extension (m:mutable.Map[String, (msg:Any ) => Unit]) 
  
  def addHandler[T<:MessageName](msgname:MessageName, thandler:T=>Unit) =  m.addOne(msgname.name,anyHandler(thandler))


val x = mMap.addHandler(MessageString,myStringHandler)


