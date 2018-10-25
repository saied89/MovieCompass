package repositories

import models.Movie
import org.jsoup.Jsoup
import repositories.MetacriticDateUtil.parseDate

typealias MetacriticHtmlString = String

fun MetacriticHtmlString.getMovies(): List<Movie> =
        Jsoup.parse(this)
            .body()
            .select(".summary_row")
            .map { element ->
                val title = element.select(".title a").html()
                val dateStr = element.select(".date_wrapper span:first-child").html()
                val date = parseDate(dateStr)
                Movie(title = title, releaseDate = date)
            }
