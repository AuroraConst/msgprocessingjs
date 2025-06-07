package com.axiommd.events

object MessageDispatcher:
    import com.raquo.laminar.api.L.{*}
    import com.raquo.airstream.ownership.ManualOwner
    
    lazy val owner = new ManualOwner()
    
    def emitEvent(name: String): Unit = {
        nameBus.emit(name)
    }
    
    val nameBus = new EventBus[String]
    val evStream = nameBus.events
    var observedValues = List.empty[String]