package api

import kotlinx.coroutines.Deferred
import models.OmdbData
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET(".")
    fun getMovieData(@Query("t") title: String): Deferred<Response<OmdbData>>
}