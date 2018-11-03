package services.OmdbCrawlerService

import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class OmdbCrawlerServiceTest: KoinTest {

    @BeforeAll
    fun setUp(){
        startKoin(listOf())
    }

    @AfterAll
    fun tearDown(){
        stopKoin()
    }
}