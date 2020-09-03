name := """play-scala-seed"""
organization := "play-hands-on"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "play-hands-on.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "play-hands-on.binders._"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  evolutions,
  specs2 % Test,
  filters,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.playframework.anorm" %% "anorm" % "2.6.4",
  "com.h2database" % "h2" % "1.4.196",
  "org.skinny-framework" %% "skinny-orm"      % "3.0.3",
  "ch.qos.logback"       %  "logback-classic" % "1.1.+",
  "org.scalatestplus.play" %% "scalatestplus-play" % "x.x.x" % "test"
)

// will be executed when invoking sbt console
initialCommands := """
import scalikejdbc._
import skinny.orm._, feature._
import org.joda.time._
skinny.DBSettings.initialize()
implicit val session = AutoSession
"""
