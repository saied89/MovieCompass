package api

import io.reactivex.Observable
import repositories.MetacriticHtmlString
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface MetacriticApi {

    @GET("available/date")
    fun getPage( @Query("page") page:Int = 0): Observable<Result<MetacriticHtmlString>>
}