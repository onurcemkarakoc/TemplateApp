package com.onurcemkarakoc.templateapp.data.remote

import com.onurcemkarakoc.templateapp.data.models.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<PopularMoviesResponse>
}