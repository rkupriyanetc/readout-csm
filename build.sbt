import play.PlayJava

name := "readout-csm"

scalaVersion := "2.11.1"

version := "1.0-SNAPSHOT"

val appDependencies = Seq(
  "be.objectify"  %% "deadbolt-java"        % "2.3.2",
  "com.feth"      %% "play-authenticate"    % "0.6.5-SNAPSHOT",
  "mysql"         %  "mysql-connector-java" % "5.1.33",
  javaCore,
  cache,
  javaWs,
  javaJdbc,
  javaEbean
)

resolvers ++= Seq(
  "Apache" at "http://repo1.maven.org/maven2/",
  "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  "play-easymail (release)" at "http://joscha.github.io/play-easymail/repo/releases/",
  "play-easymail (snapshot)" at "http://joscha.github.io/play-easymail/repo/snapshots/",
  Resolver.url("Objectify Play Repository", url("http://schaloner.github.io/releases/"))(Resolver.ivyStylePatterns),
  "play-authenticate (release)" at "http://joscha.github.io/play-authenticate/repo/releases/",
  "play-authenticate (snapshot)" at "http://joscha.github.io/play-authenticate/repo/snapshots/"
)

//  Uncomment the next line for local development of the Play Authenticate core:
//lazy val playAuthenticate = project.in(file("modules/play-authenticate")).enablePlugins(PlayJava)

lazy val root = project.in(file(".")).enablePlugins(PlayJava).settings(libraryDependencies ++= appDependencies)
  //  Uncomment the next lines for local development of the Play Authenticate core:
  //.dependsOn(playAuthenticate)
  //.aggregate(playAuthenticate)
