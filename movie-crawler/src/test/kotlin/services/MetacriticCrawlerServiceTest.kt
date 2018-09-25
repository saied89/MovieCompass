package services

import api.MetacriticApi
import io.reactivex.Observable
import models.Movie
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import repositories.MovieRepository
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

@ExtendWith(MockitoExtension::class)
internal class MetacriticCrawlerServiceTest {

    @Mock lateinit var metacriticApi: MetacriticApi
    @Mock lateinit var movieRepository: MovieRepository
    @Captor lateinit var captor: ArgumentCaptor<List<Movie>>

    @Test
    fun `crawlAllMovies returns 200 movies from 2 pages`() {

        val resBody = getMockBody()
        `when`(metacriticApi.getPage(0)).thenReturn(Observable.just(Result.response(Response.success(resBody))))

        val crawlerService = MetacriticCrawlerService(metacriticApi, movieRepository)
        crawlerService.crawlAllMovies(2)

        verify(movieRepository).initDB(captor.capture())
        assertEquals(200, captor.value.size)
    }

    private fun getMockBody(): String {
        val resBody = javaClass.getResource("../repositories/MetacriticSampleHtml.html").readText()
        return resBody
    }
}