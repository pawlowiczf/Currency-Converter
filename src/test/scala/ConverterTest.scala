import org.scalatest.funsuite.AnyFunSuite

class ConverterTest extends AnyFunSuite: 
    
    test("ExchangeSingle -> getExchangeRateDate"):
        val exchanger = new ExchangeSingle("btc", "pln")
        val rates: Double = exchanger.getExchangeRateDate("2024-06-02")

        assert( rates < 500000 )
        assert( rates > 100000 )
    
    test("ExchangeSingle - getExchangeRatesRange"):
        val exchanger = new ExchangeSingle("btc", "pln") 
        val mapList: List[ (String, Double) ] = exchanger.getExchangeRatesRange("2024-05-20", "2024-06-03") 

        println( mapList )
        assert( mapList.length > 5 ) 

    test("ExchangeAll - getExchangeRateDate"):
        val exchanger = new ExchangeAll("btc") 
        val allRates: Map[String, Double] = exchanger.getExchangeRateDate("2024-05-20") 

        assert (!allRates.isEmpty)

        
end ConverterTest