import komponents.MetacriticCrawlerKomponent
import di.appModule
import di.netwokModule
import di.dbModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get
import services.OmdbCrawlerService.OmdbCrawlerService

fun main(vararg args: String) {
    StandAloneContext.startKoin(listOf(netwokModule, appModule, dbModule))
    Main().run()
}

class Main: KoinComponent {

    fun run(){
        MetacriticCrawlerKomponent().crawlMovies(2)
        val omdbCrawlerService: OmdbCrawlerService = get()
        omdbCrawlerService.getMovieDataForInfoLessMovies()
    }
}

