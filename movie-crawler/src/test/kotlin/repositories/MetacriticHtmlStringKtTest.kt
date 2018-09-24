package repositories

import org.junit.jupiter.api.Assertions.*

internal class MetacriticHtmlStringKtTest {

    @org.junit.jupiter.api.Test
    fun getMovies() {
        val htmlStr: MetacriticHtmlString =
                javaClass.getResource("MetacriticSampleHtml.html").readText()
        val movies = htmlStr.getMovies()
        assertEquals(100, movies.size)
    }
}