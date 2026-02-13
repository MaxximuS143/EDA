package eda

import models.MovieClean

object GenreEDA {

  def frequency(movies: List[MovieClean]): Map[String, Int] = {

    movies
      .flatMap(_.genres)
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap
  }

  def averageGenresPerMovie(movies: List[MovieClean]): Double = {

    if (movies.isEmpty) 0.0
    else movies.map(_.genreCount).sum.toDouble / movies.size
  }

  def mostCommonGenre(movies: List[MovieClean]): Option[(String, Int)] = {

    val freq = frequency(movies)

    if (freq.isEmpty) None
    else Some(freq.maxBy(_._2))
  }
}

