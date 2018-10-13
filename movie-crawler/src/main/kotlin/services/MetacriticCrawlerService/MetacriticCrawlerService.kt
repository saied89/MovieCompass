package services.MetacriticCrawlerService

import api.MetacriticApi
import io.reactivex.Observable
import repositories.MovieRepository
import repositories.getMovies


class MetacriticCrawlerService(val metacriticApi: MetacriticApi,
                               val movieRepository: MovieRepository,
                               val cacheHelper: MetacriticPagesCacheHelper? = null){
    fun crawlAllMovies(pages:Int = 100, cacheResults:Boolean = true){
        Observable.fromIterable(0..pages)
                .flatMap {
                    metacriticApi.getPage(it)
                }.toList()
                .subscribe { res, err ->
                    val allMovies = res.mapIndexed { index, result ->
                        if(result.response() != null && result.response()?.isSuccessful ?: false)
                            result.response()!!.body()!!.let {
                                if(cacheResults)
                                    cacheHelper!!.cachePage(it, index)
                                it.getMovies()
                            }
                        else
                            throw MetacriticGetPageException(index, result.error())
                    }.flatten()
                    movieRepository.initDB(allMovies)
                }
    }


    private fun cacheResult(result: String, pageNum: Int){

    }

}

