package models

import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class Movie(val _id: Id<Movie> = newId(), val title: String)
