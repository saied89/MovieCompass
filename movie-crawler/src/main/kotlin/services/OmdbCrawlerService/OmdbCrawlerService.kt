package services.OmdbCrawlerService

import api.OmdbApi
import kotlinx.coroutines.runBlocking
import repositories.MovieRepository

class OmdbCrawlerService(private val omdbApi: OmdbApi,
                         private val movieRepository: MovieRepository) {
    fun getMovieDataForInfoLessMovies(){
        val infoLessMovies = movieRepository.getInfoLessMovies()
        infoLessMovies.forEach { movie ->
            runBlocking {
                val omdbDataRes = omdbApi.getMovieData(movie.title).await()
                movieRepository.setOmdbDataOfMovie(movie, omdbDataRes.body()!!)
            }
        }

    }
}