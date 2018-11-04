package repositories

import com.mongodb.client.result.UpdateResult
import models.Movie
import models.OmdbData
import java.util.*

interface MovieRepository {
    fun initDB(movies: List<Movie>)
    fun saveMovies(movies: List<Movie>)
//    fun getUpcommingMovies(): List<Movie>
//    fun getNewestReleases(): List<Movie>
    fun getMovies(date: Date = Date(), page: Int = 0): List<Movie>

    fun getInfoLessMovies(): List<Movie>

    fun setOmdbDataOfMovie(movie: Movie, omdbData: OmdbData): UpdateResult
}