package com.onurcemkarakoc.templateapp.ui.popularmovies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.onurcemkarakoc.templateapp.data.models.Result
import com.onurcemkarakoc.templateapp.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(repository: MoviesRepository) : ViewModel() {
    private val _page = MutableLiveData<Int>()

    init {
        _page.value = 0
    }

    private val _popularMovies2 = _page.switchMap { page ->
        repository.getPopularMovies().liveData.cachedIn(viewModelScope)
    }

    val popularMoviesResponse: LiveData<PagingData<Result>> = _popularMovies2
}