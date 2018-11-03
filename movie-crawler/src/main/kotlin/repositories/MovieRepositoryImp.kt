package repositories

import com.mongodb.client.MongoCollection
import models.Movie
import org.litote.kmongo.deleteMany
import org.litote.kmongo.eq
import java.util.*

class MovieRepositoryImp(val mongoCollection: MongoCollection<Movie>): MovieRepository {
    override fun initDB(movies: List<Movie>) {
        mongoCollection.deleteMany()
        mongoCollection.insertMany(movies)
    }

    override fun saveMovies(movies: List<Movie>) {
        mongoCollection.insertMany(movies)
    }

    override fun getMovies(date: Date, page: Int): List<Movie> =
            mongoCollection.find().asSequence().toList()


    override fun getInfoLessMovies(): List<Movie> =
            mongoCollection.find(Movie::omdbData eq null).asSequence().toList()

}