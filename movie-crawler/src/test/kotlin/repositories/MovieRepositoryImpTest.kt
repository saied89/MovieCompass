package repositories

import com.mongodb.client.MongoCollection
import models.Movie
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.litote.kmongo.KMongo
import org.litote.kmongo.deleteMany
import org.litote.kmongo.getCollection
import testDI.testModule

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryImpTest: KoinTest{


    val movieTestCollection: MongoCollection<Movie> by inject()
    val movieRepository: MovieRepository by inject()

    @BeforeAll
    fun setup(){
        startKoin(listOf(testModule))
    }

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