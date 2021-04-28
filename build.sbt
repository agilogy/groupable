import com.gilcloud.sbt.gitlab.{GitlabCredentials,GitlabPlugin}

organization := "com.agilogy"

name := "groupable"

version := "1.1"

crossScalaVersions := Seq("2.11.12","2.12.13")

libraryDependencies +=   "org.scalatest" %% "scalatest" % "3.0.1" % "test"

publishMavenStyle := false

// --> gitlab

GitlabPlugin.autoImport.gitlabGroupId := None
GitlabPlugin.autoImport.gitlabProjectId := Some(26236490)
GitlabPlugin.autoImport.gitlabDomain := "gitlab.com"

GitlabPlugin.autoImport.gitlabCredentials := {
    val token = sys.env.get("GITLAB_DEPLOY_TOKEN") match {
        case Some(token) => token
        case None =>
            sLog.value.warn(s"Environment variable GITLAB_DEPLOY_TOKEN is undefined, 'publish' will fail.")
            ""
    }
    Some(GitlabCredentials("Deploy-Token", token))
}

// <-- gitlab
