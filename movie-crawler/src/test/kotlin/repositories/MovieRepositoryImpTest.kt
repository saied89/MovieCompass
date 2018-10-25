package repositories

import com.mongodb.client.MongoCollection
import models.Movie
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.deleteMany
import org.litote.kmongo.getCollection

private const val DB_NAME = "movieTest"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryImpTest{


    val movieTestCollection: MongoCollection<Movie> = KMongo.createClient().getDatabase(DB_NAME).getCollection()
    val movieRepository = MovieRepositoryImp(movieTestCollection)

    @BeforeEach
    fun cleanDB(){
        movieTestCollection.deleteMany()
    }

    @Test
    fun `save 2 movies to db and check them`(){
        val movie1 = Movie(title = "title1", releaseDate = 0L)
        val movie2 = Movie(title = "title2", releaseDate = 0L)
        movieRepository.saveMovies(listOf(movie1, movie2))
        val res = movieRepository.getMovies()
        assertEquals(2, res.size)
    }
//
    @Test
    fun `save 100 movies to db and retrieve them`(){

    }

}