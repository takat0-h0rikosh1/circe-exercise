package example

import io.circe._
import io.circe.parser._

object TraversingAndModifying {

  def main(array: Array[String]): Unit = {

    val json =
      """{
        |  "id": "c730433b-082c-4984-9d66-855c243266f0",
        |  "name": "Foo",
        |  "counts": [1, 2, 3],
        |  "values": {
        |    "bar": true,
        |    "baz": 100.001,
        |    "qux": ["a", "b"]
        |  }
        |}""".stripMargin
    val doc: Json = parse(json).getOrElse(Json.Null)
    val cursor: HCursor = doc.hcursor

    val baz: Decoder.Result[Double] =
      cursor.downField("values").downField("baz").as[Double]
    val baz2: Decoder.Result[Double] =
      cursor.downField("values").get[Double]("baz")
    val qux: Decoder.Result[Seq[String]] =
      cursor.downField("values").downField("qux").as[Seq[String]]
    val secondQux: Decoder.Result[String] =
      cursor.downField("values").downField("qux").downArray.right.as[String]
    val thirdQux: Decoder.Result[String] =
      cursor
        .downField("values")
        .downField("qux")
        .downArray
        .deleteGoRight
        .as[String]

    println(baz)
    println(baz2)
    println(qux)
    println(secondQux)
    println(thirdQux)

    // name の値を逆文字列にする
    val reversedNameCursor: ACursor =
      cursor
        .downField("name")
        .withFocus(_.mapString(_.reverse))

    // カーソルを一段の階層に上げて、配列 qux の先頭データを削除してカーソルを右に移動
    val deletedHeadArray  =
      reversedNameCursor
        .up
        .downField("values")
        .downField("qux")
        .downArray
        .deleteGoRight

    val maybeJson = deletedHeadArray.top

    println(maybeJson)
  }
}
