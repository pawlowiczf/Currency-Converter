import fs2.Stream 
import java.time.format.DateTimeFormatter
import java.time.LocalDate

implicit class Pipe[A](value: A) {
    def |>[B](f: A => B): B = f(value)
}

def >^^<[B, C, D](f: (b: B, c: C) => D) = (c: C) => (b: B) => f(b, c) 
def >^^^<[B, C, D, E](f: (b: B, c: C, d: D) => E) = (d: D) => (b: B) => (c: C)  => f(b, c, d) 

sealed trait CurrencyConverter

case class ExchangeSingle(fromCurrency: String, toCurrency: String) extends CurrencyConverter:
    //
    def getExchangeRateDate(date: String): Double =
        //
        val properDate = Validators.dateValidator(date)
        
        DataFetcher.fetchCurrencyData(fromCurrency, properDate)
            |> JsonParser.parseJSON
            |> ( rates => rates.getOrElse(toCurrency, 1) )

    end getExchangeRateDate

    def getExchangeRatesRange(dateStart: String, dateEnd: String) = 
        //
        var start = Validators.dateValidator(dateStart) |> LocalDate.parse
        val end   = Validators.dateValidator(dateEnd) |> LocalDate.parse 

        var mapList: List[ (String, Double) ] = List.empty

        while ( !start.isAfter(end) ) {
            val result = getExchangeRateDate(start.toString())
            mapList = mapList :+ (start.toString(), result)

            start = start.plusDays(1)
        }

        mapList 
    end getExchangeRatesRange

end ExchangeSingle 

case class ExchangeAll(fromCurrency: String) extends CurrencyConverter:
    //
    def getExchangeRateDate(date: String): Map[String, Double] =
        //
        val properDate = Validators.dateValidator(date)

        DataFetcher.fetchCurrencyData(fromCurrency, properDate)
            |> JsonParser.parseJSON
            
    end getExchangeRateDate

    def getExchangeRatesRange(dateStart: String, dateEnd: String) = 
    //
        var start = Validators.dateValidator(dateStart) |> LocalDate.parse
        val end   = Validators.dateValidator(dateEnd) |> LocalDate.parse 

        var mapList: List[ (String, Map[String, Double]) ] = List.empty

        while ( !start.isAfter(end) ) {
            val result = getExchangeRateDate(start.toString())
            mapList = mapList :+ (start.toString(), result)

            start = start.plusDays(1)
        }

        mapList 
    end getExchangeRatesRange

object ExchangeSingle:
    //

end ExchangeSingle 


object Converter: 
    //

end Converter 
