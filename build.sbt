
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

wartremoverErrors ++= Seq(
  Wart.StringPlusAny, Wart.FinalCaseClass, Wart.JavaConversions, Wart.Null, Wart.Product, Wart.Serializable,
  Wart.LeakingSealed, Wart.While, Wart.Return, Wart.ExplicitImplicitTypes, Wart.Enumeration, Wart.FinalVal,
  Wart.TryPartial, Wart.TraversableOps, Wart.OptionPartial, Wart.ArrayEquals, ContribWart.SomeApply/*, TODO:
    ContribWart.ExposedTuples, ContribWart.OldTime */
)

wartremoverWarnings ++= wartremover.Warts.allBut(
  Wart.Nothing, Wart.DefaultArguments, Wart.Throw, Wart.MutableDataStructures, Wart.NonUnitStatements, Wart.Overloading,
  Wart.Option2Iterable, Wart.ImplicitConversion, Wart.ImplicitParameter, Wart.Recursion,
  Wart.Any, Wart.Equals, // Too many warnings because of spark's Row
  Wart.AsInstanceOf // Too many warnings because of bad DI practices
)

libraryDependencies ++= Seq(
  // The following dependencies are provided by EMR. When upgrading an EMR version, upgrade them too.
  "org.apache.spark"    %% "spark-core"                   % "2.3.0"              % "provided,test",
  "org.apache.spark"    %% "spark-sql"                    % "2.3.0"              % "provided,test",
  "org.apache.spark"    %% "spark-hive"                   % "2.3.0"              % "provided,test",
  "org.apache.spark"    %% "spark-catalyst"               % "2.3.0"              % "provided,test"
)