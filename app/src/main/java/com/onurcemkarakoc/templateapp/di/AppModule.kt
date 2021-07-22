package com.onurcemkarakoc.templateapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.onurcemkarakoc.templateapp.BuildConfig
import com.onurcemkarakoc.templateapp.data.remote.ApiService
import com.onurcemkarakoc.templateapp.data.remote.PopularMoviesDataSource
import com.onurcemkarakoc.templateapp.data.remote.TopRatedMoviesDataSource
import com.onurcemkarakoc.templateapp.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor { Timber.e("HttpLoggingInterceptor > $it") }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providePopularMoviesDataSource(apiService: ApiService) = PopularMoviesDataSource(apiService)

    @Singleton
    @Provides
    fun provideTopRatedMoviesDataSource(apiService: ApiService) =
        TopRatedMoviesDataSource(apiService)

    @Singleton
    @Provides
    fun provideMoviesRepository(
        dataSourcePopular: PopularMoviesDataSource,
        dataSourceTopRated: TopRatedMoviesDataSource
    ) = MoviesRepository(dataSourcePopular, dataSourceTopRated)

}