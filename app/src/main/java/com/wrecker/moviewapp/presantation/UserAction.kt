package com.wrecker.moviewapp.presantation

sealed interface UserAction {
    data class Search(val query: String): UserAction
    data class Details(val imdbID: String): UserAction
}