package services.MetacriticCrawlerService

import api.MetacriticApi
import io.reactivex.Observable
import models.Movie
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
                    val allMovies: List<Movie> = res.mapIndexed { index, result ->
                        val response = result.response()
                        if(response != null && response.isSuccessful ?: false)
                            response.body()!!.let {
                                cacheHelper?.let { _ ->
                                    if(cacheResults)
                                        cacheHelper.cachePage(it, index)
                                }
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

