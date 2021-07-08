package com.onurcemkarakoc.templateapp.ui.topratedmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onurcemkarakoc.templateapp.R

class TopRatedMoviesFragment : Fragment() {
    private val popularMoviesViewModel: TopRatedMovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_rated_movies, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TopRatedMoviesFragment()
    }
}