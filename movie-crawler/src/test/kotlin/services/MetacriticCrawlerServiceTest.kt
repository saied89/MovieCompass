package services

import api.MetacriticApi
import com.nhaarman.mockitokotlin2.capture
import io.reactivex.Observable
import models.Movie
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import repositories.MovieRepository
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result
import services.MetacriticCrawlerService.MetacriticCrawlerService

@ExtendWith(MockitoExtension::class)
internal class MetacriticCrawlerServiceTest {

    @Mock lateinit var metacriticApi: MetacriticApi
    @Mock lateinit var movieRepository: MovieRepository
    @Captor lateinit var captor: ArgumentCaptor<List<Movie>>

    @Test
    fun `crawlAllMovies returns 200 movies from 2 pages`() {

        val resBody = getMockBody()
        `when`(metacriticApi.getPage(ArgumentMatchers.any(Int::class.java)))
                .thenReturn(Observable.just(Result.response(Response.success(resBody))))

        val crawlerService = MetacriticCrawlerService(metacriticApi, movieRepository)
        crawlerService.crawlAllMovies(1)

        verify(movieRepository).initDB(capture(captor))
        assertEquals(200, captor.value.size)
    }

    private fun getMockBody(): String {
        val resBody = javaClass.getResource("../repositories/MetacriticSampleHtml.html").readText()
        return resBody
    }
}