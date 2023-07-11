package example

import cats.effect.{Async, ExitCode, IO, IOApp, Sync}
import cats.parse.Parser.Expectation.Fail
import io.circe.Decoder.Result
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import sttp.tapir._
import org.http4s.server.Router
import sttp.apispec.openapi.OpenAPI
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.apispec.openapi.circe.yaml._

import java.util.concurrent.atomic.AtomicReference


final case class User(name : String, uuid: String)






import org.http4s.ember.server.EmberServerBuilder

import com.comcast.ip4s.{Host, IpLiteralSyntax, Port}

object Main extends IOApp  {

  def run(args: List[String]):  IO[ExitCode] = {

    //val routes = Http4sServerInterpreter[IO]().toRoutes(RoutesQueries.getRoutesQueries())
   val routes: HttpRoutes[IO]  = EndPointsBooks.getRoutes()

    val port = sys.env
      .get("HTTP_PORT")
      .flatMap(_.toIntOption)
      .flatMap(Port.fromInt)
      .getOrElse(port"8082")


    EmberServerBuilder
      .default[IO]
      .withHost(Host.fromString("localhost").get)
      .withPort(port)
      .withHttpApp(Router("/" -> routes ).orNotFound)
      .build
      .use { server =>
        for {
          _ <- IO.println(s"Go to http://localhost:${server.address.getPort}/docs to open SwaggerUI. Press ENTER key to exit.")
          _ <- IO.readLine
        } yield ()
      }
      .as(ExitCode.Success)




  }


}











