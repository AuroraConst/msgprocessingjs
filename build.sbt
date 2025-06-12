import scala.sys.process._
import sbt._
import sbt.Keys._
import org.scalajs.linker.interface.ModuleSplitStyle


lazy val copyFileJSArtifactsToMedia = taskKey[Unit]("Copies a file from source to destination")
lazy val viteMainJSFolder = taskKey[File]("file location of main.js and main.js.map from target directory of the viteview project")
lazy val extensionMediaFolder = taskKey[File]("file folder location of the media folder of the extension")

extensionMediaFolder := {
  baseDirectory.value / "media"
}

copyFileJSArtifactsToMedia := {

    val fastOptFile = (Compile / fastLinkJSOutput ).value // / "target" / "destination.txt"
    val sourceMain =  (viteview / viteMainJSFolder).value / "main.js"
    val sourceMainMap = (viteview / viteMainJSFolder).value / "main.js.map"
    val destinationMain = baseDirectory.value / "media" / "main.js"
    val destinationMainMap = baseDirectory.value / "media" / "main.js.map"


    IO.copyFile(sourceMain, destinationMain)
    IO.copyFile(sourceMainMap, destinationMainMap)
  }

lazy val installDependencies = Def.task[Unit] {
  val base = (ThisProject / baseDirectory).value
  val log = (ThisProject / streams).value.log
  if (!(base / "node_module").exists) {
    val pb =
      new java.lang.ProcessBuilder("npm.cmd", "install")
        .directory(base)
        .redirectErrorStream(true)

    pb ! log
  }
}

lazy val open = taskKey[Unit]("open vscode")
def openVSCodeTask: Def.Initialize[Task[Unit]] =
  Def
    .task[Unit] {
      val base = baseDirectory.value
      val log = streams.value.log

      val path = base.getCanonicalPath
      val pathOut = baseDirectory.value / "out" 
      s"code.cmd  --extensionDevelopmentPath=$path" ! log
      ()
    }.dependsOn(copyFileJSArtifactsToMedia)
    // .dependsOn(installDependencies)

val commonSettings = Seq(
  scalaVersion := DependencyVersions.scala,
  // CommonJS
  scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) 
    .withModuleKind(ModuleKind.ESModule) //for scalatest to work.  not sure how much of a slowdown occurs during builds
    .withModuleSplitStyle(
      ModuleSplitStyle.SmallModulesFor(List("viteview")))
  },
  scalacOptions ++=  Seq("-Yretain-trees",//necessary in zio-json if any case classes have default parameters
  "-Xmax-inlines","60", //setting max inlines to accomodate > 32 fields in case classes
  "-Wunused:all"
  ), 

  libraryDependencies ++= Dependencies.ziojson.value,  
  // libraryDependencies  ++= Dependencies.upickle.value,
  // libraryDependencies ++= Dependencies.borerJson.value,
  libraryDependencies ++= Dependencies.scalajsmacrotaskexecutor.value,

)    

lazy val messaging = project
  .in(file("modules/messaging"))
  .settings(commonSettings,
    libraryDependencies ++= Dependencies.laminar.value,
    libraryDependencies ++= Dependencies.scalatest.value,
    externalNpm := baseDirectory.value,
  ).enablePlugins(
    ScalaJSPlugin,
    ScalablyTypedConverterExternalNpmPlugin,
  )




lazy val viteview = project
  .in(file("modules/viteview"))
  .settings(commonSettings,
  libraryDependencies ++= Dependencies.laminar.value,
  libraryDependencies ++= Dependencies.shapeless3.value,
  libraryDependencies ++= Dependencies.aurorajslibs.value,
  libraryDependencies ++= Dependencies.scalatest.value,
  // Tell Scala.js that this is an application with a main method
    scalaJSUseMainModuleInitializer := true,
  viteMainJSFolder := {
    val sourceMain = (Compile / fastLinkJSOutput ).value 
    sourceMain
  },
  externalNpm := baseDirectory.value,

  )
  .enablePlugins(
    ScalaJSPlugin,
    ScalablyTypedConverterExternalNpmPlugin,

  )




lazy val root = project
  .in(file("."))
  .settings(
    commonSettings,

    moduleName := "mrpchartjs",
    Compile / fastOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    Compile / fullOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    open := openVSCodeTask.dependsOn(Compile / fastOptJS).value,
        // CommonJS
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },

    // Compile / npmDependencies ++= Seq("@types/vscode" -> "1.84.1"),
    // Tell ScalablyTyped that we manage `npm install` ourselves
    externalNpm := baseDirectory.value,

    testFrameworks += new TestFramework("utest.runner.Framework")
    // publishMarketplace := publishMarketplaceTask.dependsOn(fullOptJS in Compile).value
  )
  .enablePlugins(
    ScalaJSPlugin,
    ScalablyTypedConverterExternalNpmPlugin
    // ScalablyTypedConverterPlugin
  )
