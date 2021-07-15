package com.onurcemkarakoc.templateapp.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.onurcemkarakoc.templateapp.data.models.MovieDetailResponse
import com.onurcemkarakoc.templateapp.data.repository.MoviesRepository
import com.onurcemkarakoc.templateapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MoviesRepository) :
    ViewModel() {
    private val _movieId = MutableLiveData<Double>()

    private val _movieDetail = _movieId.switchMap { movieId -> repository.getMovieDetail(movieId) }

    val detail: LiveData<Resource<MovieDetailResponse>> = _movieDetail

    fun start(movieId: Double) {
        _movieId.value = movieId
    }
}