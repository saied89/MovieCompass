package di

import com.mongodb.client.MongoCollection
import models.Movie
import org.koin.dsl.module.module
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import repositories.MovieRepository
import repositories.MovieRepositoryImp

private const val DB_NAME = "movieDB"

val dbModule = module {
    single { provideMongoCollection() }

    single { provideMovieRepository(get()) }
}

private fun provideMongoCollection(): MongoCollection<Movie> =
        KMongo.createClient().getDatabase(DB_NAME).getCollection()

private fun provideMovieRepository(collection: MongoCollection<Movie>): MovieRepository =
        MovieRepositoryImp(collection)
