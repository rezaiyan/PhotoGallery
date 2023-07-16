package com.github.rezaiyan.network.di

import com.github.rezaiyan.network.FlickrService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.Lazy
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideFlickrService(retrofit: Retrofit): FlickrService = retrofit.create(FlickrService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: Lazy<OkHttpClient>,
    ): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val moshiConverterFactory = MoshiConverterFactory.create(moshi)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.get())
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    companion object {
        private const val TIMEOUT = 15L
        private const val BASE_URL = "https://api.flickr.com/services/"
    }

}