package com.segunfrancis.darumandroidassessment.di

import com.google.gson.Gson
import com.segunfrancis.darumandroidassessment.data.MoviesService
import com.segunfrancis.darumandroidassessment.util.AppConstants.BASE_URL
import com.segunfrancis.darumandroidassessment.util.AppConstants.CONNECTIVITY_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private fun provideGson(): Gson {
        return Gson().newBuilder().setLenient().create()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun provideClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(provideLoggingInterceptor())
            .callTimeout(CONNECTIVITY_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECTIVITY_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(): MoviesService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideClient())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
            .create(MoviesService::class.java)
    }
}
