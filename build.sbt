
name := "SparkPitfallMeetupNb"

version := "0.1"

scalaVersion := "2.11.8"

javaOptions ++= Seq("-Xms512M", "-Xmx8192M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")

scalacOptions ++= Seq(
  "-feature", "-deprecation", "-unchecked", "-explaintypes",
  "-encoding", "UTF-8", // yes, this is 2 args
  "-language:reflectiveCalls", "-language:implicitConversions", "-language:postfixOps", "-language:existentials",
  "-language:higherKinds",
  // http://blog.threatstack.com/useful-scala-compiler-options-part-3-linting
  "-Xcheckinit", "-Xexperimental", "-Xfatal-warnings", /*"-Xlog-implicits", */"-Xfuture", "-Xlint",
  "-Ywarn-dead-code", "-Ywarn-inaccessible", "-Ywarn-numeric-widen", "-Yno-adapted-args", "-Ywarn-unused-import",
  "-Ywarn-unused"
)

libraryDependencies ++= Seq(
  "org.apache.spark"    %% "spark-core"                   % "2.3.0",
  "org.apache.spark"    %% "spark-sql"                    % "2.3.0",
  "org.apache.spark"    %% "spark-hive"                   % "2.3.0",
  "org.apache.spark"    %% "spark-catalyst"               % "2.3.0",
  "org.apache.spark"    %% "spark-core"                   % "2.3.0" classifier "tests",
  "org.apache.spark"    %% "spark-sql"                    % "2.3.0" classifier "tests",
  "org.apache.spark"    %% "spark-catalyst"               % "2.3.0" classifier "tests",
  "org.scalatest"       %% "scalatest"                    % "3.0.3"
)