package di

import api.MetacriticApi
import api.OmdbApi
import com.mongodb.client.MongoCollection
import models.Movie
import org.koin.dsl.module.module
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import repositories.MovieRepository
import repositories.MovieRepositoryImp
import services.MetacriticCrawlerService.MetacriticCrawlerService
import services.MetacriticCrawlerService.MetacriticPagesCacheHelper
import services.OmdbCrawlerService.OmdbCrawlerService

val appModule = module {

    single { provideMetacriticCrawlerService(get(), get(), get()) }

    single { provideMetacriticPagesCacheHelper() }

    single { provideOmdbCrawlerService(get(), get()) }

}


private fun provideMetacriticCrawlerService(metacriticApi: MetacriticApi, movieRepository: MovieRepository, cacheHelper: MetacriticPagesCacheHelper) =
        MetacriticCrawlerService(metacriticApi, movieRepository, cacheHelper)

private fun provideMetacriticPagesCacheHelper() = MetacriticPagesCacheHelper()

private fun provideOmdbCrawlerService(omdbApi: OmdbApi, movieRepository: MovieRepository): OmdbCrawlerService = OmdbCrawlerService(omdbApi, movieRepository)

