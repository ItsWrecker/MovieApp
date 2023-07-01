package com.wrecker.moviewapp.domain

import com.google.gson.annotations.SerializedName

//{"Title":"Spider-Man",
//    "Year":"2002",
//    "imdbID":"tt0145487",
//    "Type":"movie",
//    "Poster":"https://m.media-amazon.com/images/M/MV5BZDEyN2NhMjgtMjdhNi00MmNlLWE5YTgtZGE4MzNjMTRlMGEwXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg"
//}
data class Response(
    @SerializedName("Search")
    val search: List<MovieEntity>
)

data class MovieEntity(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Poster")
    val poster: String
)

data class MovieDetails(
    @SerializedName("Rated")
    val rating: String,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("Awards")
    val awards: String,
    @SerializedName("Poster")
    val poster: String
)
