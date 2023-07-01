package com.wrecker.moviewapp.data.di

import com.wrecker.moviewapp.data.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(

    ): OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder().addHeader("apikey", "a16081d9").build()
        chain.proceed(request)
    }.build()

    @Provides
    @Singleton
    internal fun provideMovieApi(
        okHttpClient: OkHttpClient
    ): MovieApi = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com")
        //.client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApi::class.java)


}