package limpieza

import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import models.Genre

object GenreCleaner {

  private def normalizeJson(raw: String): String = {
    raw
      .replace("'", "\"")
      .replace("None", "null")
  }

  def parseGenres(raw: String): List[String] = {

    if (raw == null || raw.trim.isEmpty) {
      List.empty[String]
    } else {

      val normalized = normalizeJson(raw)

      parse(normalized) match {

        case Left(_) =>
          List.empty[String]

        case Right(json) =>
          json.as[List[Genre]] match {

            case Left(_) =>
              List.empty[String]

            case Right(genres) =>
              genres.map(_.name)
          }
      }
    }
  }
}

