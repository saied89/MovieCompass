package di

import api.MetacriticApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import org.litote.kmongo.json
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private const val MOVIES_LIST_URL = "https://www.metacritic.com/browse/dvds/release-date/"

private const val MOVIES_DATA_URL: String = ""

const val METACRITIC_API_NAME = "METACRITIC_API_NAME"


val NetwokModule = module {
    single { createOkHttpClient() }
    single(METACRITIC_API_NAME) { provideMetacriticRetrofit(get()) }
    single { createWebService<MetacriticApi>(get(METACRITIC_API_NAME)) }
}


fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor {
                it.request().newBuilder().addHeader(
                        "User-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
                ).run {
                    it.proceed(this.build())
                }
            }
//            .addInterceptor {
//                println("*** ${it.request().method()} Request to ${it.request().url()}")
//                println("Headers: ${it.request().headers().toMultimap()}")
//                println("Body: ${it.request().body()}")
//                it.proceed(it.request())
//            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
}

fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

fun provideMoshiConverterFactory(moshi: Moshi) : MoshiConverterFactory = MoshiConverterFactory.create(moshi)

fun provideScalarConverterFactory(): ScalarsConverterFactory = ScalarsConverterFactory.create()

fun provideMetacriticRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(MOVIES_LIST_URL)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

fun provideApiRetrofit(client: OkHttpClient, moshiConverterFactory: MoshiConverterFactory) : Retrofit = Retrofit.Builder()
        .baseUrl(MOVIES_DATA_URL)
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()




inline fun <reified T> createWebService(retrofit: Retrofit): T = retrofit.create(T::class.java)