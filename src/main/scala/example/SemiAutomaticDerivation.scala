package example

import example.SemiAutomaticDerivation.User
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.generic.JsonCodec

object SemiAutomaticDerivation {

  case class Foo(a: Int, b: String, c: Boolean)
  @JsonCodec case class Bar(i: Int, s: String)
  case class User(id: Long, firstName: String, lastName: String)

  implicit val fooDecoder: Decoder[Foo] = deriveDecoder
  implicit val fooEncoder: Encoder[Foo] = deriveEncoder

  def main(array: Array[String]): Unit = {
    val foo = Foo(1, "foo", true)
    val json = foo.asJson
    println(json)

    val unit = json.as[Foo]
    println(unit)

    val json2 = Bar(13, "Qux").asJson
    println(json2)

    import UserCodec._

    val user = User(123, "first name", "last name")

    val json3 = user.asJson
    val unit3 = json.as[User]

    println(json3)
    println(unit3)

  }

}

object UserCodec {
  implicit val decoderUser: Decoder[User] =
    Decoder.forProduct3("id", "first_name", "last_name")(User)
  implicit val encoderUser: Encoder[User] =
    Encoder.forProduct3("id", "first_name", "last_name")(u =>
      (u.id, u.firstName, u.lastName))
}
