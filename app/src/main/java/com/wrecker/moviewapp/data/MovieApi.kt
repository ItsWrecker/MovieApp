package com.wrecker.moviewapp.data

import com.wrecker.moviewapp.domain.MovieDetails
import com.wrecker.moviewapp.domain.MovieEntity
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
//    @Headers("?apikey: a16081d9")
    suspend fun searchMovie(
        @Query("s")
        query: String,
        @Query("apikey")
        apiKey: String = "a16081d9"
    ): Response<com.wrecker.moviewapp.domain.Response>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i")
        imdbID: String,
        @Query("apikey")
        apiKey: String = "a16081d9"
    ): Response<MovieDetails>
}