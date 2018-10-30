package utils

import arrow.core.Either
import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import models.Movie
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import repositories.MovieRepository
import repositories.MovieRepositoryImp
import testDI.DB_NAME

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DataBaseInitUtilsKtTest{

    var movieTestCollection: MongoCollection<Movie> = KMongo.createClient().getDatabase(DB_NAME).getCollection()
    var movieRepository: MovieRepository = MovieRepositoryImp(movieTestCollection)

    @BeforeEach
    fun setupEach(){
        movieTestCollection.drop()
        movieTestCollection = KMongo.createClient().getDatabase(DB_NAME).getCollection()
    }

    private fun insertDuplicate(): Either<MongoException, Unit> {
        val movie1 = Movie(title = "saied", releaseDate = 0)
        val movie2 = Movie(title = "saied", releaseDate = 0) //_id is different
        val res: Either<MongoException, Unit> =
                try {
                    movieRepository.saveMovies(listOf(movie1, movie2))
                    Either.right(Unit)
                } catch (exp: MongoException) {
                    Either.left(exp)
                }
        return res
    }

    @Test
    fun `without uniqueIndex insertion of duplicate movie succeeds`(){
        val res: Either<MongoException, Unit> =
                insertDuplicate()
        assert(res.isRight()) //Doesn't fail
    }

    @Test
    fun `with uniqueIndex insertion of duplicate movie fails`(){
        createUniqueMovieIndices(movieTestCollection)
        val res: Either<MongoException, Unit> =
                insertDuplicate()
        assert(res.isLeft()) //Fails
    }
}