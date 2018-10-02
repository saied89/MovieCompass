import Komponents.Crawler
import di.NetwokModule
import org.koin.standalone.StandAloneContext

fun main(args: Array<String>){
    StandAloneContext.startKoin(listOf(NetwokModule))
    Crawler().crawlMovies()
}

