package repositories

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object MetacriticDateUtil {

    private const val METACRITIC_DATE_PATTERN = "MMMM d, yyyy"

    private val dateTimeFormatter = DateTimeFormatter.ofPattern(METACRITIC_DATE_PATTERN, Locale("en", "US"))

    fun parseDate(dateString: String): Long =
            LocalDate.parse(dateString, dateTimeFormatter).toEpochDay()

    fun formatDate(epochDay: Long): String =
            LocalDate.ofEpochDay(epochDay).format(dateTimeFormatter)
}