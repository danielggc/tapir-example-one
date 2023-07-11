package example

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.HttpRoutes
import sttp.apispec.openapi.OpenAPI
import sttp.tapir.{PublicEndpoint, endpoint}
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir._
import sttp.tapir.swagger.bundle.SwaggerInterpreter

import java.util.concurrent.atomic.AtomicReference
import sttp.tapir.swagger.bundle.SwaggerInterpreter


object EndPointsBooks   {


  import io.circe.generic.auto._
  import sttp.tapir.generic.auto._
  import sttp.tapir.json.circe._
  import cats.syntax.all._
  import cats.effect._



  case class Author(name: String)
  case class Book(title: String, year: Int, author: Author)

  val Books: AtomicReference[List[Book]] = new AtomicReference(
    List(
      Book("The Sorrows of Young Werther", 1774, Author("Johann Wolfgang von Goethe")),
      Book("Iliad", -8000, Author("Homer")),
      Book("Nad Niemnem", 1888, Author("Eliza Orzeszkowa")),
      Book("The Colour of Magic", 1983, Author("Terry Pratchett")),
      Book("The Art of Computer Programming", 1968, Author("Donald Knuth")),
      Book("Pharaoh", 1897, Author("Boleslaw Prus"))
    )
  )

  val booksListing: PublicEndpoint[Unit, Unit, List[Book], Any] = endpoint.get
    .in("books")
    .in("list" / "all")
    .out(jsonBody[List[Book]])

  val addBook =  endpoint.post
    .in("books")
    .in("add")
    .in(
      jsonBody[Book]
        .description(" endPoint para agregar un libro")
        .example( Book("el señor de los anillos ",1988,Author("tolkine")))
    )


  val bookListingRouste: HttpRoutes[IO] = Http4sServerInterpreter[IO].toRoutes( booksListing.serverLogicSuccess((_ => IO( Books.get() ) ) ) )
  val addBookRoute: HttpRoutes[IO] = Http4sServerInterpreter[IO].toRoutes( addBook.serverLogicSuccess(book => IO( Books.getAndUpdate(books => books :+ book ):Unit) ) )

  val swaggerUIRoutes: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(
      SwaggerInterpreter().fromEndpoints[IO](List(booksListing, addBook), "egemplo tapir books ", "1.0.0")
    )

  def getRoutes(): HttpRoutes[IO]  ={
    import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
    import sttp.apispec.openapi.circe.yaml._

    val openApi: OpenAPI = OpenAPIDocsInterpreter().toOpenAPI(List(addBook, booksListing), "My Bookshop", "1.0")
    val yml: String = openApi.toYaml

    bookListingRouste <+> addBookRoute <+> swaggerUIRoutes <+>  bookListingRouste
   }

  def makeClientRequest(): Unit = {
    import sttp.client3._
    import sttp.tapir.client.sttp.SttpClientInterpreter

    val client = SttpClientInterpreter().toQuickClient(booksListing, Some(uri"http://localhost:8082"))

    val result: Either[Unit, List[Book]] = client(Some(3))

    print("Result of listing request with limit 3: " + result)
  }


}
