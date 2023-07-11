package example

import cats.effect.IO
import org.http4s.dsl.Http4sDsl
import sttp.apispec.openapi.OpenAPI
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.{Endpoint, PublicEndpoint, endpoint, query, stringBody}
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object RoutesQueries extends Http4sDsl[IO] {

  import sttp.apispec.openapi.circe.yaml._

  val pading  = query[String]("uiid").and(query[Option[Int]]("data"))



  val helloWorld: PublicEndpoint[String, Unit, String, Any] =
    endpoint
      .in("hello")
      .get
      .in(query[String]("name"))
      .out(stringBody)

  val helloWorldTow: Endpoint[Unit, String, Unit, String, Any] =
    endpoint
      .get
      .in("hello")
      .in("v1")
      .in(query[String]("name"))
      .out(stringBody)



  val helloWorldRoutes: ServerEndpoint[Any, IO]    = helloWorld.serverLogicSuccess(name => IO.pure(s"Hello, $name!"))
  val helloWorldRoutesTow: ServerEndpoint[Any, IO] = helloWorldTow.serverLogicSuccess(name => IO.pure(s"Hello, $name!"))


  val  all: List[ServerEndpoint[Any, IO]] = List( helloWorldRoutes, helloWorldRoutesTow )

  val docEndpoints: List[ServerEndpoint[Any, IO]] = SwaggerInterpreter()
    .fromEndpoints(List( helloWorldTow, helloWorld ), "app", "1.0.0")


  def getRoutesQueries(): List[ServerEndpoint[Any, IO]] = {
    val openApi: OpenAPI = OpenAPIDocsInterpreter().toOpenAPI(List(helloWorld, helloWorldTow), "My Bookshop", "1.0")
    val yml: String = openApi.toYaml
    docEndpoints ++ all
  }

  def initYaml(): String ={
    val openApi: OpenAPI = OpenAPIDocsInterpreter().toOpenAPI(List(helloWorld, helloWorldTow), "My Bookshop", "1.0")
    val yml: String = openApi.toYaml
    yml
  }



}