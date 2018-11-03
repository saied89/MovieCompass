package api

import di.netwokModule
import kotlinx.coroutines.runBlocking
import models.OmdbData
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.core.parameter.parametersOf
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.koin.standalone.get
import retrofit2.Response

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class OmdbApiTest: KoinTest {
    lateinit var api: OmdbApi

    @BeforeAll
    fun setup(){
        startKoin(listOf(netwokModule))
    }

    @Test
    fun `api returns data`() = runBlocking{
        val server = MockWebServer()
        val readText = this@OmdbApiTest::class.java.getResource("OmdpSample.json").readText()
        server.enqueue(
                MockResponse()
                        .setBody(readText)
        )
        api = get{
            parametersOf(server.url(""))
        }
        val res: Response<OmdbData> = api.getMovieData("jnj").await()
        assert(res.isSuccessful)
        server.shutdown()
    }

    @AfterAll
    fun tearDown(){
        stopKoin()
    }

}