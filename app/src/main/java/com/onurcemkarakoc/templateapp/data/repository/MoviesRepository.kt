package com.onurcemkarakoc.templateapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.onurcemkarakoc.templateapp.data.remote.PopularMoviesDataSource
import com.onurcemkarakoc.templateapp.data.remote.TopRatedMoviesDataSource
import com.onurcemkarakoc.templateapp.utils.performGetOperation
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val dataSourcePopular: PopularMoviesDataSource,
    private val dataSourceTopRated: TopRatedMoviesDataSource
) {

    fun getPopularMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 10000

            ),
            pagingSourceFactory = { dataSourcePopular }
        )

    fun getTopRatedMovies() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 10000

        ),
        pagingSourceFactory = { dataSourceTopRated }
    )

    fun getMovieDetail(movie_id: Double) = performGetOperation {
        dataSourcePopular.getMovieDetail(movie_id)
    }
}