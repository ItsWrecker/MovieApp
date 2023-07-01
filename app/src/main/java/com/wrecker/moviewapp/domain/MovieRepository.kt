package com.wrecker.moviewapp.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun searchMovie(input: String): Flow<MovieEvents<List<MovieEntity>>>

    suspend fun getMovieDetails(imdbID: String): Flow<MovieEvents<MovieDetails>>

}