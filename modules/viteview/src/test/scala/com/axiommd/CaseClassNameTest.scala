package com.axiommd


import testutils.*

class CaseClassNameTest extends SjsTesting:
  case class TestCaseClass(name: String,simpleName:String = this.getClass.getSimpleName.dropRight(1)) 

  "this" should {
    "work" in {
      val testCaseClass = TestCaseClass("hello")
      s"$testCaseClass" should be ("TestCaseClass(hello,TestCaseClass)")
    }
  }
