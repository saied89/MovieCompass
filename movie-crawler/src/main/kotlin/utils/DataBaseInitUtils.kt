package utils

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import models.Movie
import org.bson.conversions.Bson

fun createUniqueMovieIndices(collection: MongoCollection<Movie>): String =
        collection.createIndex(Indexes.descending("title"), IndexOptions().unique(true))
