import bintray.Keys._

organization := "com.agilogy"

name := "groupable"

version := "1.1"

crossScalaVersions := Seq("2.10.7","2.11.12","2.12.6")

libraryDependencies +=   "org.scalatest" %% "scalatest" % "3.0.1" % "test"

publishMavenStyle := false

// --> bintray

seq(bintrayPublishSettings:_*)

repository in bintray := "scala"

bintrayOrganization in bintray := Some("agilogy")

packageLabels in bintray := Seq("scala")

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

// <-- bintray
