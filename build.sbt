
name := "negative"

organization := "com.mazebrains"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.3"


//seq(webSettings :_*)
enablePlugins(JettyPlugin)

//resolvers ++= Seq("Local Maven Repository A" at "file:///home/peter/.m2/repository")

//scalacOptions := Seq("-unchecked", "-deprecation")
scalacOptions ++= Seq("-deprecation")

// force tests to run serially so we dont get concurrent update errors.
parallelExecution in Test := false



/*
// see http://comments.gmane.org/gmane.comp.lang.scala.simple-build-tool/1609
unmanagedResourceDirectories in Compile <++= baseDirectory { base =>
 (base / "src" / "main" / "scala") ::
 (base / "src" / "main" / "resources") ::
 Nil
}
*/
// new sbt 1.x
unmanagedResourceDirectories in Compile += baseDirectory.value / "src/main/scala"



libraryDependencies ++= {
 val wicketV = "8.6.1"
 Seq(
  "org.apache.wicket" % "wicket-core" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-extensions" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-util" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-request" % wicketV withSources() withJavadoc(),
//  "org.apache.wicket" % "wicket-datetime" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-ioc" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-guice" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-devutils" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-native-websocket-core" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-native-websocket-javax" % wicketV
 )
}




// https://mvnrepository.com/artifact/com.google.inject/guice
libraryDependencies += "com.google.inject" % "guice" % "4.2.2"

// https://mvnrepository.com/artifact/com.google.inject.extensions/guice-servlet
libraryDependencies += "com.google.inject.extensions" % "guice-servlet" % "4.2.2"


libraryDependencies += "ch.timo-schmid" %% "slf4s-api" % "1.7.30"

//// https://mvnrepository.com/artifact/org.slf4j/slf4j-jdk14
libraryDependencies += "org.slf4j" % "slf4j-jdk14" % "1.7.26"


// https://mvnrepository.com/artifact/com.typesafe.scala-logging/scala-logging
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"



libraryDependencies += "junit" % "junit" % "4.10" % "test"

// https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
libraryDependencies += "javax.servlet" % "javax.servlet-api" % "4.0.1" % "provided"



