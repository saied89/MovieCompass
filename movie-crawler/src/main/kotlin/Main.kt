import Komponents.MetacriticCrawlerKomponent
import di.AppModule
import di.NetwokModule
import org.koin.standalone.StandAloneContext

fun main(vararg args: String){
    StandAloneContext.startKoin(listOf(NetwokModule, AppModule))
    MetacriticCrawlerKomponent().crawlMovies()
}

