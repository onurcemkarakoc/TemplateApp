package com.onurcemkarakoc.templateapp.data.repository

import com.onurcemkarakoc.templateapp.data.remote.MoviesDataSource
import com.onurcemkarakoc.templateapp.utils.performGetOperation
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val dataSource: MoviesDataSource) {

    fun getPopularMovies(page: Int) = performGetOperation {
        dataSource.getPopularMovies(page)
    }
}