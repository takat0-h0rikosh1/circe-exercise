package example

object ParsingJson {

  import io.circe._, io.circe.parser._

  def main(array: Array[String]): Unit = {
    val jsonString =
      """{
        |  "foo" : "foo value",
        |  "bar" : {
        |    "bar_child" : "bar child value"
        |  },
        |  "array":[
        |    { "content" : 1 },
        |    { "content" : 2 },
        |    { "content" : 3 },
        |    { "content" : 4 }
        |  ]
        |}""".stripMargin

    val parsed = parse(jsonString)

    println(parsed)

    val badJsonString = "bad_json"

    val badJsonParsed = parse(badJsonString)

    println(badJsonParsed)

    badJsonParsed match {
      case Left(failure) => println(s"invalid json: $failure")
      case Right(_) => println("Yay, got some JSON!")
    }


  }
}
