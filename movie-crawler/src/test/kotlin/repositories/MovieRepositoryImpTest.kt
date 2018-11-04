package repositories

import com.mongodb.client.MongoCollection
import com.squareup.moshi.Moshi
import di.netwokModule
import models.Movie
import models.OmdbData
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.litote.kmongo.deleteMany
import testDI.testModule

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryImpTest: KoinTest{


    val movieTestCollection: MongoCollection<Movie> by inject()
    val movieRepository: MovieRepository by inject()

    @BeforeAll
    fun setup(){
        startKoin(listOf(testModule, netwokModule))
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
    fun `getInfoLessMovies returns only infoless movies`(){
        val movie1 = Movie(title = "title1", releaseDate = 0L)
        val movie2 = Movie(title = "title2", releaseDate = 0L, omdbData = getSampleOMDBInfo())
        movieRepository.saveMovies(listOf(movie1, movie2))

        val res = movieRepository.getInfoLessMovies()
        assert(res.size == 1)
        assert(res[0].equals(movie1))
    }

    private fun getSampleOMDBInfo(): OmdbData{
        val moshi: Moshi = get()
        val adapter = moshi.adapter(OmdbData::class.java)

        val jsonText = javaClass.getResource("../api/OmdpSample.json").readText()
        return adapter.fromJson(jsonText)!!
    }

    @AfterAll
    fun tearDown(){
        stopKoin()
    }

}