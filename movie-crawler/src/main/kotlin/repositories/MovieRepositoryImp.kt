package repositories

import com.mongodb.client.MongoCollection
import models.Movie
import services.MetacriticCrawlerService
import java.util.*

class MovieRepositoryImp(val mongoCollection: MongoCollection<Movie>): MovieRepository {
    override fun initDB(movies: List<Movie>) {
        print(movies)
    }

    override fun saveMovies(movies: List<Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMovies(date: Date, page: Int): List<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}