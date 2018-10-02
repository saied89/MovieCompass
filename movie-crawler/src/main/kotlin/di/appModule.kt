package di

import api.MetacriticApi
import org.koin.dsl.module.module
import repositories.MovieRepository
import repositories.MovieRepositoryImp
import services.MetacriticCrawlerService

val AppModule = module {
    single { provideMovieRepository() }

    single { provideMetacriticCrawlerService(get(), get()) }
}

private fun provideMovieRepository(): MovieRepository = MovieRepositoryImp()

private fun provideMetacriticCrawlerService(metacriticApi: MetacriticApi, movieRepository: MovieRepository) =
        MetacriticCrawlerService(metacriticApi, movieRepository)