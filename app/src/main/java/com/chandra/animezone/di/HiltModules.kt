package com.chandra.animezone.di

import android.content.Context
import androidx.room.Room
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.repository.database.AnimeDAO
import com.chandra.animezone.repository.database.AnimeDatabase
import com.chandra.animezone.repository.network.AnimeAPI
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(CONSTANTS.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }
    @Singleton
    @Provides
    fun getAnimeAPI( retrofit: Retrofit) : AnimeAPI {
        return retrofit.create(AnimeAPI::class.java)
    }

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context : Context) : AnimeDatabase{
        return Room.databaseBuilder(context, AnimeDatabase::class.java, "AnimeDatabase")
            .build()
    }

    @Singleton
    @Provides
    fun getAnimeDAO(animeDatabase: AnimeDatabase) : AnimeDAO{
        return animeDatabase.getDAO()
    }
}