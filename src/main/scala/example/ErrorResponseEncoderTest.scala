package example

import io.circe.generic.auto._, io.circe.syntax._

object ErrorResponseEncoderTest {

  case class ErrorResponse(
      errorType: String,
      message: String,
      errorDetails: Seq[ErrorDetail] = Seq()
  )

  case class ErrorDetail(
      errorType: String,
      message: String
  )

  def main(args: Array[String]): Unit = {
    println(ErrorResponse("status", "message").asJson)
  }
}
