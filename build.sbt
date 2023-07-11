import Dependencies._

ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "tapir-example-one",
    libraryDependencies += munit % Test
  )

val TapirVersion = "1.6.0"
val http4sVersion = "0.23.14"
val circe           = "0.14.5"
val yamlTapir = "0.4.0"
//documentacion endPoinds
//enablePlugins(OpenapiCodegenPlugin)
//openapiSwaggerFile := baseDirectory.value / "swagger.yaml"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,

  "io.circe" %% "circe-core" % circe,
  "io.circe" %% "circe-generic" % circe,
  "io.circe" %% "circe-parser" % circe,

  "com.softwaremill.sttp.tapir" %% "tapir-core" % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics" % TapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-http4s-server" % TapirVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
  "org.http4s" %% "http4s-ember-server" % "0.23.22",


  "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % "1.6.0",

  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui" % TapirVersion,

"com.softwaremill.sttp.tapir" %% "tapir-redoc-bundle" % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % TapirVersion,

  "com.softwaremill.sttp.apispec" %% "apispec-model" % yamlTapir,

// only model classes, root: OpenAPI
"com.softwaremill.sttp.apispec" %% "openapi-model" % yamlTapir,
// circe encoders for the model classes
"com.softwaremill.sttp.apispec" %% "openapi-circe" % yamlTapir,
// extension method for OpenAPI to convert to yaml
"com.softwaremill.sttp.apispec" %% "openapi-circe-yaml" % yamlTapir,

// only model classes, root: AsyncAPI
"com.softwaremill.sttp.apispec" %% "asyncapi-model" % yamlTapir,
// circe encoders for the model classes
"com.softwaremill.sttp.apispec" %% "asyncapi-circe" % yamlTapir,
// extension method for AsyncAPI to convert to yaml
"com.softwaremill.sttp.apispec" %% "asyncapi-circe-yaml" %yamlTapir ,



)

