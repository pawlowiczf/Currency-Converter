import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.ContentTypes
import scala.concurrent.Future

object Main extends App {
	//
	given system: ActorSystem = ActorSystem("scala-web-server")
	given executionContext: ExecutionContextExecutor = system.dispatcher

	def readFile(filename: String): Future[String] = {
		import scala.concurrent.ExecutionContext.Implicits.global
		Future {
			val source = scala.io.Source.fromFile(filename)
			val content = source.mkString
			source.close()
			content
		}
	}

	def getCurrencyRoute =
		path("get-currency") {
			post {
				formFields("from_currency", "to_currency", "amount") { (fromCurrency, toCurrency, amount)
					=> 
						val value: Double = amount.toDouble * OperationSingle.findExchangeRateDate(fromCurrency, toCurrency) 
						val render = s"<li class=\"list-group-item bg-primary text-white\">$value</li>"
						complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, render))
				}
			}
		}

	def basicRoute = 
		path("") {
			get {
				val htmlFile: Future[String] = readFile("index.html")
				onSuccess(htmlFile) { 
					content => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, content))
				}
			}
		}

	val route = concat(
		getCurrencyRoute,
		basicRoute,
	)

	val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

	println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
	scala.io.StdIn.readLine()

	bindingFuture
		.flatMap(_.unbind())
		.onComplete(_ => system.terminate())

}
