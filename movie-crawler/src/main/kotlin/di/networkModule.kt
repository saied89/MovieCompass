package di

import api.MetacriticApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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