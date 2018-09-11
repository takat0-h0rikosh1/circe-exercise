package example

object EncodingAndDecoding {

  import io.circe.syntax._
  import io.circe.parser.decode

  def main(array: Array[String]): Unit = {
    val json = List(1, 2, 3).asJson
    json.noSpaces
    println(json)

    val unit = json.as[List[Int]]
    println(unit)

    val unit2 = decode[List[Int]]("[1,2,3]")
    println(unit2)
  }
}
