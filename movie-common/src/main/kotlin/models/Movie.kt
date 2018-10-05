package models

import org.litote.kmongo.Id

data class Movie(val _id: Id<Movie>, val title: String)
