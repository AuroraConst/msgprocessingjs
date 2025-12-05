addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.20.1")

addSbtPlugin("org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta44")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.14.4")
resolvers ++= Resolver.sonatypeOssRepos("snapshots")
