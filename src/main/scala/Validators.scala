import java.time.format.DateTimeFormatter
import scala.util.Try
import java.time.LocalDate
import scala.util.Success
import scala.util.Failure

object Validators {
    //
    def dateValidator(data: String): String = 
        val format: String = "yyyy-MM-dd" 
        val formatter = DateTimeFormatter.ofPattern(format) 
        
        Try ( LocalDate.parse(data, formatter) ) match 
            case Success(value) => value.toString()
            case Failure(exception) => ""
    end dateValidator 
}
