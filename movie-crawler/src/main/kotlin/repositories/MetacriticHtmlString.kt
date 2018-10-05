package repositories

import models.Movie
import org.jsoup.Jsoup

typealias MetacriticHtmlString = String

fun MetacriticHtmlString.getMovies(): List<Movie> =
        Jsoup.parse(this)
            .body()
            .select(".summary_row")
            .map { element ->
                val title = element.select(".title a").html()
                Movie(title = title)
            }