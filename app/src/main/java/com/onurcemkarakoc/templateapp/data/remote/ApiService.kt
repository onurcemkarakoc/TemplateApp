package com.onurcemkarakoc.templateapp.data.remote

import com.onurcemkarakoc.templateapp.BuildConfig
import com.onurcemkarakoc.templateapp.data.models.MovieDetailResponse
import com.onurcemkarakoc.templateapp.data.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): Response<MoviesResponse>

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): Response<MoviesResponse>

    @GET("{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Double,
        @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): Response<MovieDetailResponse>

}