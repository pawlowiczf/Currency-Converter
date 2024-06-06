
import org.scalatest.funsuite.AnyFunSuite
import java.time.LocalDate

class OperationSingleTest extends AnyFunSuite: 
    
    test("Operation - averageRateRange"):
        val exchanger = new ExchangeSingle("btc", "pln")
        val operation = new OperationSingle(exchanger)

        val average = operation.averageRateRange("2024-05-20", "2024-06-02")
        
        assert(average > 0) 
        assert(average < 300000)

    test("Operation - highestRateRange"):
        val exchanger = new ExchangeSingle("btc", "pln")
        val operation = new OperationSingle(exchanger) 

        val highest: (String, Double) = operation.highestRateRange("2024-05-20", "2024-06-03") 

        val date: String = highest._1 
        val splittedDate = date.split("-", 3)


        assert( highest._2 > 100000 )
        assert( highest._2 < 500000 )
        assert( splittedDate(0) == "2024" )

end OperationSingleTest
