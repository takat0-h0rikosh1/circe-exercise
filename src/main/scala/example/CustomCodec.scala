package example

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}

object CustomCodec {

  class Thing(val foo: String, val bar: Int)

  implicit val encoder: Encoder[Thing] = new Encoder[Thing] {
    final def apply(t: Thing): Json = Json.obj(
      ("foo", Json.fromString(t.foo)),
      ("bar", Json.fromInt(t.bar))
    )
  }

  implicit val decoder: Decoder[Thing] = new Decoder[Thing] {
    final def apply(c: HCursor): Result[Thing] =
      for {
        foo <- c.downField("foo").as[String]
        bar <- c.downField("bar").as[Int]
      } yield new Thing(foo, bar)
  }

  def main(args: Array[String]): Unit = {
    simple

  }

  def simple: Unit = {

    val thing = new Thing("test", 123)

    import io.circe.parser._
    import io.circe.syntax._

    val jsonString = thing.asJson.noSpaces
    println(parse(jsonString))
    println(decode[Thing](jsonString))

  }

  def piggyBack: Unit = {
    import java.time.Instant
  }
}
