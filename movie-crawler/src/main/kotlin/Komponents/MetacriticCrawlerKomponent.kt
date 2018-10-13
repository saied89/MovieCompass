package Komponents

import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import services.MetacriticCrawlerService.MetacriticCrawlerService

class MetacriticCrawlerKomponent: KoinComponent {
    private val crawlerService: MetacriticCrawlerService by inject()
    fun crawlMovies(){
        crawlerService.crawlAllMovies()
    }
}
