package repositories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MetacriticHtmlStringKtTest {

    @Test
    fun `100 movies are retuned from sample html`() {
        val htmlStr: MetacriticHtmlString =
                javaClass.getResource("MetacriticSampleHtml.html").readText()
        val movies = htmlStr.getMovies()
        assertEquals(100, movies.size)
    }

    @Test
    fun `empty array is returned from wrongly formatted string`(){
        val notMetacriticHtml = "kndaknwlda<kmcsl><>kmk<dfwlm><<<>982820ndka<ncn<"

        val movies = notMetacriticHtml.getMovies()
        assert(movies.isEmpty())
    }

    @Test
    fun `empty array is returned from empty string`(){
        val movies = "".getMovies()
        assert(movies.isEmpty())
    }


}