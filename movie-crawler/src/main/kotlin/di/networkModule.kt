package di

import api.MetacriticApi
import api.OmdbApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.configuration2.builder.fluent.Configurations
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val MOVIES_LIST_URL = "https://www.metacritic.com/browse/dvds/release-date/"

private const val MOVIES_DATA_URL: String = "http://www.omdbapi.com/"

private const val METACRITIC_API_NAME = "METACRITIC_API_NAME"

private const val OMDB_API_NAME = "OMDB_API_NAME"

private const val CONNECT_TIME_OUT: Long = 60

val netwokModule = module {
    single(METACRITIC_API_NAME) { createMetacriticOkHttpClient() }
    single(METACRITIC_API_NAME) { provideMetacriticRetrofit(get(METACRITIC_API_NAME)) }

    single(OMDB_API_NAME) {
        val configuration = Configurations().properties(File("movie.properties"))
        with(configuration) {
            getString("omdb_apiKey")
        }
    }
    single(OMDB_API_NAME) { createOMDBOkHttpClient(get(OMDB_API_NAME)) }
    single { provideMoshi() }
    single { provideMoshiConverterFactory(get()) }
    single(OMDB_API_NAME) { (httpUrl: HttpUrl?) ->
        provideApiRetrofit(get(OMDB_API_NAME), get(), httpUrl)
    }

    single { createWebService<MetacriticApi>(get(METACRITIC_API_NAME)) }
    single { (httpUrl: HttpUrl) ->
        createWebService<OmdbApi>(get(OMDB_API_NAME, parameters = { parametersOf(httpUrl) }))
    }
}

private fun createMetacriticOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request().newBuilder().addHeader(
                        "User-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
                ).run {
                    chain.proceed(this.build())
                }
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .readTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .build()
}

private fun createOMDBOkHttpClient(apiKey: String): OkHttpClient =
        OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val url = chain.request().url().newBuilder().addQueryParameter("apikey",apiKey).build()
                    val request = chain.request().newBuilder().url(url).build()
                    chain.proceed(request)
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

private fun provideMetacriticRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(MOVIES_LIST_URL)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

private fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

private fun provideApiRetrofit(
    client: OkHttpClient,
    moshiConverterFactory: MoshiConverterFactory,
    baseUrl: HttpUrl?
): Retrofit {
    val builder = Retrofit.Builder()
    builder.baseUrl(baseUrl ?: HttpUrl.get(MOVIES_DATA_URL)) // baseUrl should be null except for testing
    return builder
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
}
private inline fun <reified T> createWebService(retrofit: Retrofit): T = retrofit.create(T::class.java)
