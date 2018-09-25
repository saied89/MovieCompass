package services

import api.MetacriticApi
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import repositories.MovieRepository
import repositories.getMovies
import java.lang.Exception

class MetacriticCrawlerService(val metacriticApi: MetacriticApi, val movieRepository: MovieRepository){
    fun crawlAllMovies(pages:Int = 100){
        (0..pages).map {pageInd ->
            metacriticApi.getPage(pageInd)
        }.apply {
//            Observable.zip(this, {
//
//            })
            Observable.fromIterable(this)
                    .flatMap {task ->
                        task
                                .observeOn(Schedulers.trampoline())
                                .map {
                                    if(it.response() != null && it.response()?.isSuccessful ?: false)
                                        it.response()!!.body()?.getMovies()
                                    else
                                        throw Exception("Error retrieving page ${it.response()?.raw()?.request()?.url()?.queryParameter("page") ?: 0}")
                                }
                    }.toList()
                    .subscribe { result, throwable ->
                        result.apply {
                            val res = toList()
                                    .filterNotNull()
                                    .flatten()
                            movieRepository.initDB(res)
                        }
                    }
        }

    }

}