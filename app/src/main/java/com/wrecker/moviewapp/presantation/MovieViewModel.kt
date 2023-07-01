package com.wrecker.moviewapp.presantation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrecker.moviewapp.domain.MovieEvents
import com.wrecker.moviewapp.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movie = MutableStateFlow(UiState())
    val movie: StateFlow<UiState> = _movie


    fun processUserEvent(userEvents: UserAction) = viewModelScope.launch {
        when (userEvents) {
            is UserAction.Details -> {
                try {
                    movieRepository.getMovieDetails(userEvents.imdbID).collectLatest {
                        when (it) {
                            is MovieEvents.Error -> _movie.value = _movie.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                            is MovieEvents.Loading -> _movie.value = _movie.value.copy(isLoading = true)
                            is MovieEvents.Success -> _movie.value = _movie.value.copy(
                                movieDetails = it.data,
                                isSearchScreen = false
                            )
                        }
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
            is UserAction.Search -> {
                try {
                    movieRepository.searchMovie(userEvents.query).collectLatest {
                        when(it){
                            is MovieEvents.Error -> _movie.value = _movie.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                            is MovieEvents.Loading -> _movie.value = _movie.value.copy(isLoading = true)
                            is MovieEvents.Success -> _movie.value = _movie.value.copy(
                                movieData = it.data
                            )
                        }
                    }
                }catch (exception: Exception){
                    exception.printStackTrace()
                }
            }
        }
    }

}