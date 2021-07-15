package com.onurcemkarakoc.templateapp.data.remote

import com.onurcemkarakoc.templateapp.base.BaseDataSource
import javax.inject.Inject

class MoviesDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {
    suspend fun getPopularMovies(page: Int) = getResult { apiService.getPopularMovies(page) }
    suspend fun getTopRatedMovies(page: Int) = getResult { apiService.getTopRatedMovies(page) }
    suspend fun getMovieDetail(movie_id: Double) = getResult { apiService.getMovieDetail(movie_id) }
}