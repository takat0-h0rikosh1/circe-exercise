package example

import io.circe.generic.auto._, io.circe.syntax._

object AutomaticDerivation {

  case class Person(name: String)
  case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

  def main(args: Array[String]): Unit = {
    // 必要な型クラスインスタンスを自動的に導出する実装に shapeless が利用されている
    println(Greeting("Hey", Person("Chris"), 3).asJson)
  }
}
