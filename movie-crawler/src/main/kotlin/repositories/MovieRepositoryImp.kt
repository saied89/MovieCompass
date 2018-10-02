package repositories

import models.Movie
import services.MetacriticCrawlerService

class MovieRepositoryImp: MovieRepository {
    override fun initDB(movies: List<Movie>) {
        print(movies)
    }
}