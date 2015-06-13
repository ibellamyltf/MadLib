name := "MadLib"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.scala-lang.modules" %% "scala-async" % "0.9.2",
  "org.scalanlp" %% "epic-pos-en" % "2015.1.25",
  "org.jsoup" % "jsoup" % "1.7.2"
)

