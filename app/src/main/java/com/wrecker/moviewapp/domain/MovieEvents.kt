package com.wrecker.moviewapp.domain

sealed interface MovieEvents<out R> {

    data class  Loading(val message: String): MovieEvents<Nothing>
    data class Error(val message: String?=null, val cause: Exception?=null): MovieEvents<Nothing>
    data class Success<R>(val data: R ): MovieEvents<R>
}