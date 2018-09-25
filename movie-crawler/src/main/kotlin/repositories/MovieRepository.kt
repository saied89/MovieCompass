package repositories

import models.Movie

interface MovieRepository {
    fun initDB(movies: List<Movie>)
}