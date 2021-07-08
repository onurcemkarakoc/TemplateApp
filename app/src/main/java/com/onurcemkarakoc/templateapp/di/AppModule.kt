package com.onurcemkarakoc.templateapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.onurcemkarakoc.templateapp.BuildConfig
import com.onurcemkarakoc.templateapp.data.remote.ApiService
import com.onurcemkarakoc.templateapp.data.remote.MoviesDataSource
import com.onurcemkarakoc.templateapp.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder().addHeader("api_key", BuildConfig.API_KEY).build()
            )
        }
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMoviesDataSource(apiService: ApiService) = MoviesDataSource(apiService)

    @Singleton
    @Provides
    fun provideMoviesRepository(dataSource: MoviesDataSource) = MoviesRepository(dataSource)

}