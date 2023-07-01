package com.wrecker.moviewapp.data.repository

import android.util.Log
import com.wrecker.moviewapp.data.MovieApi
import com.wrecker.moviewapp.domain.MovieDetails
import com.wrecker.moviewapp.domain.MovieEntity
import com.wrecker.moviewapp.domain.MovieEvents
import com.wrecker.moviewapp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    val TAG = "MovieRepository"
    override suspend fun getMovieDetails(imdbID: String): Flow<MovieEvents<MovieDetails>> = flow {
        try {
            emit(MovieEvents.Loading("Fetching the movies from api"))
            val response = movieApi.getMovieDetails(imdbID)
            Log.e(TAG, response.toString())
            if (response.isSuccessful && response.code().toString().startsWith("20")) {
                val data = response.body()
                    ?: return@flow emit(MovieEvents.Error("Error while fetching the response"))
                return@flow emit(MovieEvents.Success(data))
            } else return@flow emit(MovieEvents.Error("Something went wrong!!"))
        } catch (exception: Exception) {
            Log.e("Nework", exception.toString())
            exception.printStackTrace()
            return@flow emit(MovieEvents.Error(exception.message, exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchMovie(input: String): Flow<MovieEvents<List<MovieEntity>>> = flow {
        try {
            emit(MovieEvents.Loading("Fetching the movies from api"))
            val response = movieApi.searchMovie(input)
            Log.e(TAG, response.toString())
            if (response.isSuccessful && response.code().toString().startsWith("20")) {
                val data = response.body()
                    ?: return@flow emit(MovieEvents.Error("Error while fetching the response"))
                return@flow emit(MovieEvents.Success(data.search))
            } else return@flow emit(MovieEvents.Error("Something went wrong!!"))
        } catch (exception: Exception) {
            Log.e("Nework", exception.toString())
            exception.printStackTrace()
            return@flow emit(MovieEvents.Error(exception.message, exception))
        }
    }.flowOn(Dispatchers.IO)
}