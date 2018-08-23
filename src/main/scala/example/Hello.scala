package example

object Hello extends Greeting with App {

  import GenericLevelDerivation._
  import io.circe.syntax._

  val level1 = Level.L1
  val encoded = level1.asInstanceOf[Level].asJson

  println(encoded)
  println(greeting)
}

trait Greeting {
  lazy val greeting: String = "hello"
}

sealed abstract class Level(val value: String)

object Level {
  case object L1 extends Level("level1")
  case object L2 extends Level("level2")
  case object L3 extends Level("level3")
}


object GenericLevelDerivation {

  import example.Level._
  import io.circe.{Encoder, Json}

  implicit val encode: Encoder[Level] = Encoder.instance {
    case l @ L1 => Json.fromString(l.value)
    case l @ L2 => Json.fromString(l.value)
    case l @ L3 => Json.fromString(l.value)
  }
}
