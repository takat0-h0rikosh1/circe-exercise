package example

import io.circe.{Decoder, DecodingFailure, HCursor, Json}

object Hello extends App {

  import GenericLevelDerivation._
  import io.circe.syntax._

  val level1 = Level.L1

  // encode 処理
  val encoded = level1.asInstanceOf[Level].asJson
  println(encoded)

  // decode 成功
  val decodedRight = encoded.as[Level]
  println(decodedRight)

  // decode 失敗
  val invalidJson = Json.obj(("level", Json.fromString("invalid json")))
  val decodedLeft = invalidJson.as[Level]
  println(decodedLeft)
}

sealed abstract class Level(val value: String)

object Level {
  case object L1 extends Level("level1")
  case object L2 extends Level("level2")
  case object L3 extends Level("level3")

  def fromString(s: String): Option[Level] =
    PartialFunction.condOpt(s)  {
      case L1.value => L1
      case L2.value => L2
      case L3.value => L3
    }
}


object GenericLevelDerivation {

  import example.Level._
  import io.circe.{Encoder, Json}

  val key = "level"

  implicit val encode: Encoder[Level] = Encoder.instance {
    case l @ L1 => Json.obj((key, Json.fromString(l.value)))
    case l @ L2 => Json.obj((key, Json.fromString(l.value)))
    case l @ L3 => Json.obj((key, Json.fromString(l.value)))
  }

  implicit val decode: Decoder[Level] = new Decoder[Level] {
    final def apply(c: HCursor): Decoder.Result[Level] =
      for {
        value <- c.downField(key).as[String]
        maybeLevel = Level.fromString(value)
        result <- maybeLevel
          .map(Right(_))
          .getOrElse(Left(DecodingFailure(s"invalid value: $value", c.downField(key).history)))
      } yield result
  }
}
