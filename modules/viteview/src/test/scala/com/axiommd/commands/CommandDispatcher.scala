package com.axiommd.commands
import zio.json.*
object CommandDispatcher:
  import com.raquo.laminar.api.L.{*}
  
  private def cmdToJson(cmd: Command): String = cmd match {
      case cc:PatientTrackerUpdateRow => cc.toJson
    }
  def emitCommand(cmd: Command): Unit = 
    cmdBus.emit(cmd)
  
  
  private val cmdBus = new EventBus[Command]
  private val jsonBus = new EventBus[String]


  val cmdToJsonStream = cmdBus.events.map{command =>
      command match {
        case cc:PatientTrackerUpdateRow => cc.toJson
      }
    }

  val jsonToCmdStream = jsonBus.events.map{json =>
      json.fromJson[PatientTrackerUpdateRow] match {
        case Left(err) => throw new Exception(s"Failed to decode command from JSON: $err")
        case Right(cmd) => cmd
      }
    } 
  