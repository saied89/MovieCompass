import Komponents.MetacriticCrawlerKomponent
import di.appModule
import di.netwokModule
import di.dbModule
import org.koin.standalone.StandAloneContext

fun main(vararg args: String){
    StandAloneContext.startKoin(listOf(netwokModule, appModule, dbModule))
    MetacriticCrawlerKomponent().crawlMovies(2)
}

