package services.MetacriticCrawlerService

import api.MetacriticApi
import io.reactivex.Observable
import repositories.MovieRepository
import repositories.getMovies
import kotlin.Exception



class MetacriticCrawlerService(val metacriticApi: MetacriticApi, val movieRepository: MovieRepository){
    fun crawlAllMovies(pages:Int = 100, cacheResults:Boolean = true){
        Observable.fromIterable(0..pages)
                .flatMap {
                    metacriticApi.getPage(it)
                }.toList()
                .subscribe { res, err ->
                    val allMovies = res.mapIndexed { index, result ->
                        if(result.response() != null && result.response()?.isSuccessful ?: false)
                            result.response()!!.body()!!.let {
                                it.getMovies()

                            }
                        else
                            throw MetacriticGetPageException(index, result.error())
                    }.flatten()
                    movieRepository.initDB(allMovies)
                }
    }
}

private fun cacheResult(result: String, pageNum: Int){

}

}

class MetacriticGetPageException(pageNum: Int, error: Throwable?): Exception("There was an error getting page \"$pageNum\": ${error?.message ?: "Error with empty message!"}")