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

private const val DB_NAME = "movieDB"

val AppModule = module {
    single { provideMovieRepository(get()) }

    single { provideMetacriticCrawlerService(get(), get(), get()) }

    single { provideMongoCollection() }

    single { provideMetacriticPagesCacheHelper() }
}

private fun provideMovieRepository(collection: MongoCollection<Movie>): MovieRepository = MovieRepositoryImp(collection)

private fun provideMetacriticCrawlerService(metacriticApi: MetacriticApi, movieRepository: MovieRepository, cacheHelper: MetacriticPagesCacheHelper) =
        MetacriticCrawlerService(metacriticApi, movieRepository, cacheHelper)

private fun provideMongoCollection(): MongoCollection<Movie> =
        KMongo.createClient().getDatabase(DB_NAME).getCollection()

private fun provideMetacriticPagesCacheHelper() = MetacriticPagesCacheHelper()

