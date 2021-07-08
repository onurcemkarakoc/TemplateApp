package com.onurcemkarakoc.templateapp.ui.topratedmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.onurcemkarakoc.templateapp.data.models.PopularMoviesResponse
import com.onurcemkarakoc.templateapp.data.repository.MoviesRepository
import com.onurcemkarakoc.templateapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMovieViewModel @Inject constructor(repository: MoviesRepository) : ViewModel() {
    private val _page = MutableLiveData<Int>()

    private val _popularMovies = _page.switchMap { page ->
        repository.getPopularMovies(page)
    }

    val popularMoviesResponse: LiveData<Resource<PopularMoviesResponse>> = _popularMovies


    fun start(page: Int) {
        _page.value = page
    }
}