package repositories

import models.Movie
import java.util.*

interface MovieRepository {
    fun initDB(movies: List<Movie>)
    fun saveMovies(movies: List<Movie>)
//    fun getUpcommingMovies(): List<Movie>
//    fun getNewestReleases(): List<Movie>
    fun getMovies(date: Date = Date(), page: Int = 0): List<Movie>
}