package com.chandra.animezone.di

import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.repository.network.AnimeAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class HiltModules {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(CONSTANTS.BASE_URL)
            .build()
    }
    @Singleton
    @Provides
    fun getAnimeAPI( retrofit: Retrofit) : AnimeAPI {
        return retrofit.create(AnimeAPI::class.java)
    }
}