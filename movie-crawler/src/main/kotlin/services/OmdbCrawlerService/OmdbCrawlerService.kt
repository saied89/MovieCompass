package services.OmdbCrawlerService

import api.OmdbApi
import repositories.MovieRepository

class OmdbCrawlerService(val omdbApi: OmdbApi,
                         val movieRepository: MovieRepository) {

}