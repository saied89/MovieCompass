package services.MetacriticCrawlerService

import di.appModule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MetacriticPagesCacheHelperTest: KoinTest{

    @BeforeAll
    fun setup(){
        startKoin(listOf(appModule))
    }

    val metacriticPagesCacheHelper: MetacriticPagesCacheHelper by inject()

    @Test
    fun `cache content succeeds and has same contents`(){
        val testContent = "kjqkjcakbca"
        metacriticPagesCacheHelper.cachePage(testContent, 0)
        val res = metacriticPagesCacheHelper.getCachedPage(0)
        assertNotNull(res)
        assertEquals(testContent, res)
    }

    @Test
    fun `read cache returns null when file doesnt exist`(){
        val res = metacriticPagesCacheHelper.getCachedPage(8713618)
        assertNull(res)
    }

    @AfterAll
    fun tearDown(){
        stopKoin()
    }
}