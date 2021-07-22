package com.onurcemkarakoc.templateapp.data.remote

import androidx.paging.PagingSource
import com.onurcemkarakoc.templateapp.data.models.Result
import com.onurcemkarakoc.templateapp.utils.Constants.START_INDEX
import com.onurcemkarakoc.templateapp.utils.Constants.getResult
import javax.inject.Inject


class PopularMoviesDataSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, Result>() {

    suspend fun getMovieDetail(movie_id: Double) = getResult { apiService.getMovieDetail(movie_id) }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: START_INDEX

        return try {

            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getPopularMovies(position)
            val responseData = mutableListOf<Result>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}