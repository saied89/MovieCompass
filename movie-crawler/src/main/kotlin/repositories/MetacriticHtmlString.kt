package repositories

import models.Movie
import org.jsoup.Jsoup
import repositories.MetacriticDateUtil.parseDate

typealias MetacriticHtmlString = String

fun MetacriticHtmlString.getMovies(): List<Movie> =
        Jsoup.parse(this)
            .body()
            .select(".clamp-summary-wrap")
            .map { element ->
                val title = element.select("a.title").html()
                val dateStr = element.select(".clamp-details span:nth-child(2)").html()
                val date = parseDate(dateStr)
                Movie(title = title, releaseDate = date)
            }
