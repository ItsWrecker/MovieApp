package com.wrecker.moviewapp.data.di

import com.wrecker.moviewapp.data.repository.MovieRepositoryImpl
import com.wrecker.moviewapp.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @Binds
    @Singleton
    fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}