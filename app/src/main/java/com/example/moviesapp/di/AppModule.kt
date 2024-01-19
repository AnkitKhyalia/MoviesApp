package com.example.moviesapp.di

import com.example.moviesapp.data.AppConstants
import com.example.moviesapp.data.apiservice.ApiService
import com.example.moviesapp.data.datasource.GetMoviesDataSource
import com.example.moviesapp.data.datasource.GetMoviesDataSourceImpl
import com.example.moviesapp.data.repository.GetMoviesRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val httpLoggingInterceptor= HttpLoggingInterceptor().apply {
            level= HttpLoggingInterceptor.Level.BASIC
        }
        val httpClient= OkHttpClient().newBuilder()
            .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT,ConnectionSpec.MODERN_TLS))
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES)
            .apply {
                addInterceptor(httpLoggingInterceptor)
            }

        val moshi= Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(AppConstants.App_Base_Url)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCryptoDataSource(apiService: ApiService):GetMoviesDataSource{
        return GetMoviesDataSourceImpl(apiService)
    }
    @Provides
    @Singleton
    fun providesCryptoRepositories(cryptoDataSource: GetMoviesDataSource):GetMoviesRepository{
        return GetMoviesRepository(cryptoDataSource)
    }
}