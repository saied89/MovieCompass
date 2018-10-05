package repositories

import di.AppModule
import models.Movie
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryImpTest: KoinTest{


    //real repository
    val movieRepository: MovieRepository by inject()

    @BeforeAll
    fun setup(){
        startKoin(listOf(AppModule))
    }

    @Test
    fun `save 2 movies to db and check them`(){
        val movie1 = Movie(title = "title1")
        val movie2 = Movie(title = "title2")
        movieRepository.saveMovies(listOf(movie1, movie2))
        val res = movieRepository.getMovies()
        assertEquals(2, res.size)
    }

    @Test
    fun `save 100 movies to db and retrieve them`(){

    }

}