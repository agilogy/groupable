import bintray.Keys._

organization := "com.agilogy"

name := "groupable"

version := "1.0.1"

crossScalaVersions := Seq("2.10.4","2.11.5")

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"

publishMavenStyle := false

// --> bintray

seq(bintrayPublishSettings:_*)

repository in bintray := "scala"

bintrayOrganization in bintray := Some("agilogy")

packageLabels in bintray := Seq("scala")

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

// <-- bintray
