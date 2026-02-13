package models

case class Movie(
                  title: String,
                  genresRaw: String
                )

case class MovieClean(
                       title: String,
                       genres: List[String],
                       genreCount: Int
                     )

