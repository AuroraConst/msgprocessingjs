addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.16.0")

addSbtPlugin("org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta44")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.34+5-5dfe5fb6-SNAPSHOT")
resolvers ++= Resolver.sonatypeOssRepos("snapshots")
