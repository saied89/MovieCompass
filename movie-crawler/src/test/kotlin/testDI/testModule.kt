package testDI

import com.mongodb.client.MongoCollection
import models.Movie
import org.koin.dsl.module.module
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import repositories.MovieRepository
import repositories.MovieRepositoryImp

const val DB_NAME = "movieTest"

val testModule = module {
    single { provideMovieRepository(get()) }

    single { provideMongoCollection() }
}

private fun provideMongoCollection(): MongoCollection<Movie> =
        KMongo.createClient().getDatabase(DB_NAME).getCollection()

private fun provideMovieRepository(collection: MongoCollection<Movie>): MovieRepository = MovieRepositoryImp(collection)