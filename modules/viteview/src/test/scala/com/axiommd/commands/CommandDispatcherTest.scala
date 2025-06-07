package com.axiommd.commands


import com.axiommd.testutils.*

class CommandDispatcherTest extends LaminarWordSpecTesting:
  var observedValues = List.empty[String]

  "this" should {
    "work" in {
      CommandDispatcher.cmdToJsonStream.foreach(
        cmdJson => 
          observedValues = cmdJson :: observedValues
      )

      val testCommand = PatientTrackerUpdateRow("test")
      val testCommand1 = PatientTrackerUpdateRow("test1")
      CommandDispatcher.emitCommand(testCommand)
      CommandDispatcher.emitCommand(testCommand1)

      info(s"observedValues: $observedValues")
      observedValues.size should be(2)
      // observedValues should contain(testCommand.name)
      

    }
  }
