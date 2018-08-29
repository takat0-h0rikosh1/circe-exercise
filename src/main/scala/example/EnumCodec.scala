package example

import io.circe.Decoder

object EnumCodec {

  import GenericLevelDerivation._
  import io.circe.syntax._
  import io.circe.parser.decode

  def main(array: Array[String]): Unit = {
    val level1 = Level.L1
    val encoded = level1.asInstanceOf[Level].asJson
    val decoded = decode(encoded.noSpaces)
    val decodeOverridden = decode("level4".asJson.noSpaces)
    println(encoded)
    println(decoded)
    println(decodeOverridden)
  }
}


sealed abstract class Level(val value: String)

object Level {
  case object L1 extends Level("level1")
  case object L2 extends Level("level2")
  case object L3 extends Level("level3")

  val all = Seq(L1,L2,L3)
  def fromString(s: String): Option[Level] =
    all.find(_.value == s)
}


object GenericLevelDerivation {

  import example.Level._
  import io.circe.{Encoder, Json}

  implicit val encodeLevel: Encoder[Level] = Encoder.instance {
    l: Level => Json.fromString(l.value)
  }

  implicit val decodeLevel: Decoder[Level] =
    Decoder.decodeString.map(Level.fromString(_).getOrElse(L1))
}
