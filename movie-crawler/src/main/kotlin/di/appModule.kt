package di

import api.MetacriticApi
import com.mongodb.client.MongoCollection
import models.Movie
import org.koin.dsl.module.module
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import repositories.MovieRepository
import repositories.MovieRepositoryImp
import services.MetacriticCrawlerService.MetacriticCrawlerService
import services.MetacriticCrawlerService.MetacriticPagesCacheHelper

val AppModule = module {

    single { provideMetacriticCrawlerService(get(), get(), get()) }

    single { provideMetacriticPagesCacheHelper() }
}


private fun provideMetacriticCrawlerService(metacriticApi: MetacriticApi, movieRepository: MovieRepository, cacheHelper: MetacriticPagesCacheHelper) =
        MetacriticCrawlerService(metacriticApi, movieRepository, cacheHelper)

private fun provideMetacriticPagesCacheHelper() = MetacriticPagesCacheHelper()

