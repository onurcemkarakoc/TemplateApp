package com.onurcemkarakoc.templateapp.ui.topratedmovies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.onurcemkarakoc.templateapp.data.models.Result
import com.onurcemkarakoc.templateapp.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMovieViewModel @Inject constructor(repository: MoviesRepository) : ViewModel() {
    private val _page = MutableLiveData<Int>()

    private val _topRatedMovies = _page.switchMap { page ->
        repository.getTopRatedMovies().liveData.cachedIn(viewModelScope)
    }

    val topRatedMoviesResponse: LiveData<PagingData<Result>> = _topRatedMovies


    fun start(page: Int) {
        _page.value = page
    }
}