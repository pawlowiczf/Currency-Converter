import scala.util.matching.Regex

object JsonParser:
    //
    val currencyJSONStructure: Regex = """"([a-z]{2,4})": ([0-9]{1,20}\.[0-9]{1,20})""".r

    // Parsing file and finds strings that matches: [name of currency]: [value of currency]
    def parseJSON(jsonStr: Option[String]) = 
        //
        if jsonStr.isEmpty then throw new IllegalArgumentException("Error - parseJSON, no data provided")
        var currencyMap: Map[String, Double] = Map.empty

        currencyJSONStructure
            .findAllMatchIn(jsonStr.get)
            .foreach( pattern => currencyMap = currencyMap + (pattern.group(1) -> pattern.group(2).toDouble) )
        
        currencyMap 
    end parseJSON


end JsonParser 
