import limpieza.GenreCleaner
import eda.GenreEDA
import models.MovieClean

import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {

    val filePath = "movies.csv"

    val source = Source.fromFile(filePath, "UTF-8")

    val lines =
      try {
        source.getLines().toList
      } finally {
        source.close()
      }

    if (lines.isEmpty) {
      println("El archivo esta vacio")
      sys.exit(1)
    }

    val header =
      lines.head.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")

    val genresIndex = header.indexOf("genres")

    if (genresIndex == -1) {
      println("No se encontro la columna genres")
      sys.exit(1)
    }

    val dataLines = lines.tail

    val cleanedMovies: List[MovieClean] =
      dataLines.map { line =>

        val columns =
          line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")

        val rawGenres =
          if (genresIndex < columns.length)
            columns(genresIndex)
          else ""

        val cleanedRaw =
          rawGenres
            .stripPrefix("\"")
            .stripSuffix("\"")

        val genres =
          GenreCleaner.parseGenres(cleanedRaw)

        MovieClean(
          title = "",
          genres = genres,
          genreCount = genres.size
        )
      }

    println("=== Frecuencia de generos ===")
    GenreEDA.frequency(cleanedMovies)
      .toSeq
      .sortBy(-_._2)
      .foreach(println)

    println()
    println("Promedio de generos por pelicula:")
    println(GenreEDA.averageGenresPerMovie(cleanedMovies))

    println()
    println("Genero mas comun:")
    println(GenreEDA.mostCommonGenre(cleanedMovies))
  }
}


