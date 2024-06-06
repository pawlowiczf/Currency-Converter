import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{File, PrintWriter}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DataFetcher {

    def fetchCurrencyData(baseCurrency: String = "pln", date: String = "latest"): Option[String] = {
        val baseUrlPrimary = s"https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@$date/v1/currencies/$baseCurrency.json"
        val baseUrlFallback = s"https://$date.currency-api.pages.dev/v1/currencies/$baseCurrency.json"

        // Downloads data from url and 
        def fetchData(url: String): Try[String] = Try {
            Source.fromURL(url).mkString
        }

        fetchData(baseUrlPrimary) match {
            case Success(data) => Some(data)
            case Failure(_) =>
            println(s"Primary URL failed, trying fallback URL.")
            fetchData(baseUrlFallback) match {
                case Success(data) => Some(data)
                case Failure(_) =>
                println(s"Both primary and fallback URLs failed.")
                None
            }   
        }
    }

    def saveToFile(data: String, filePath: String): Unit = {
        val file = new File(filePath)
        file.getParentFile.mkdirs() // Create directories if they do not exist
        val writer = new PrintWriter(file)
        writer.write(data)
        writer.close()
    }


}
