name := "akka-iroha"

version := "0.1"

scalaVersion := "2.12.8"

val grpcVersion = "1.16.1"

organization := "com.ekaya"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

libraryDependencies += "net.i2p.crypto" % "eddsa" % "0.3.0"
libraryDependencies += "com.google.protobuf" % "protobuf-java" % "3.6.1" % "protobuf"
libraryDependencies += "io.grpc" % "grpc-protobuf" % grpcVersion

bintrayRepository := "akka-iroha"

bintrayOrganization in bintray := None

enablePlugins(AkkaGrpcPlugin)

akkaGrpcGeneratedSources := Seq(AkkaGrpc.Client)
