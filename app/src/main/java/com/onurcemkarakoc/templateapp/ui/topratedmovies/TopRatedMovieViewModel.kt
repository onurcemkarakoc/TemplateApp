package com.onurcemkarakoc.templateapp.ui.topratedmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.onurcemkarakoc.templateapp.data.models.MoviesResponse
import com.onurcemkarakoc.templateapp.data.repository.MoviesRepository
import com.onurcemkarakoc.templateapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMovieViewModel @Inject constructor(repository: MoviesRepository) : ViewModel() {
    private val _page = MutableLiveData<Int>()

    private val _topRatedMovies = _page.switchMap { page ->
        repository.getTopRatedMovies(page)
    }

    val topRatedMoviesResponse: LiveData<Resource<MoviesResponse>> = _topRatedMovies


    fun start(page: Int) {
        _page.value = page
    }
}