import java.time.LocalDate

abstract class Operation:
    //
    def saveContentToFile(content: String, path: String) = 
        DataFetcher.saveToFile(content, path)
end Operation 

class OperationSingle(converter: ExchangeSingle) extends Operation:
    //
    def averageRateRange(dateStart: String, dateEnd: String) =
        //
        converter.getExchangeRatesRange(dateStart, dateEnd) 
            .map( (_, rate) => rate )
            |> ( values => values.sum / values.length )
    end averageRateRange

    def highestRateRange(dateStart: String, dateEnd: String): (String, Double) = 
        //
        converter.getExchangeRatesRange(dateStart, dateEnd) 
            .maxBy( (_: String, rate: Double) => rate )
    end highestRateRange 

end OperationSingle 

class OperationAll(converter: ExchangeAll) extends Operation: 
    //
    
end OperationAll

object OperationSingle: 
    //
    def findExchangeRateDate(fromCurrency: String, toCurrency: String) =
        //
        val currentDate: String = LocalDate.now().toString()
        val exchanger = ExchangeSingle (fromCurrency, toCurrency) 

        currentDate |> exchanger.getExchangeRateDate
    end findExchangeRateDate 

end OperationSingle 

object OperationAll extends App: 
    //
    implicit class ShouldWrap[A](value: A): 
        def should[B](expected: B) = value equals expected  

    def be[B](value: B) = value 

    println( ExchangeSingle("pln", "pln").getExchangeRateDate("2024-05-06") should be (1.0) )
    println( String.valueOf(2).toInt should be (2) )
    
end OperationAll 

