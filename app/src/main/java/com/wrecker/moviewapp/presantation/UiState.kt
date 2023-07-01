package com.wrecker.moviewapp.presantation

import com.wrecker.moviewapp.domain.MovieDetails
import com.wrecker.moviewapp.domain.MovieEntity

data class UiState(
    val isLoading: Boolean = false,
    val isSearchScreen: Boolean = true,
    val movieData: List<MovieEntity>?=null,
    val movieDetails: MovieDetails?=null,
    val error: String?=null
)
