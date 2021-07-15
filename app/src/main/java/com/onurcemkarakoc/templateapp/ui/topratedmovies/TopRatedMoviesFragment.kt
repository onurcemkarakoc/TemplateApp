package com.onurcemkarakoc.templateapp.ui.topratedmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onurcemkarakoc.templateapp.databinding.FragmentTopRatedMoviesBinding
import com.onurcemkarakoc.templateapp.ui.MainActivity
import com.onurcemkarakoc.templateapp.ui.adapters.MoviesAdapter
import com.onurcemkarakoc.templateapp.ui.moviedetail.MovieDetailFragment
import com.onurcemkarakoc.templateapp.utils.Resource
import com.onurcemkarakoc.templateapp.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment(), MoviesAdapter.MovieItemListener {
    private val topRatedMoviesViewModel: TopRatedMovieViewModel by viewModels()
    private var binding: FragmentTopRatedMoviesBinding? = null
    private lateinit var adapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopRatedMoviesBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topRatedMoviesViewModel.start(1)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter(this)
        val layoutManager = object : GridLayoutManager(context, 2, VERTICAL, false) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                lp.width = (width / spanCount) - 32
                lp.height = ((width / spanCount) * 3 / 2) - 32
                return true
            }
        }
        binding?.popularRc?.layoutManager = layoutManager
        binding?.popularRc?.addItemDecoration(SpacesItemDecoration(2, 8f, 8f, 8f, 8f))
        binding?.popularRc?.adapter = adapter

    }

    private fun setupObservers() {
        topRatedMoviesViewModel.topRatedMoviesResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { response ->
                        if (response.results.isNotEmpty())
                            adapter.setItems(response.results)
                    }
                    binding?.loadingPb?.visibility = View.GONE
                    binding?.wrongTv?.visibility = View.GONE
                    binding?.popularRc?.visibility = View.VISIBLE
                }

                Resource.Status.ERROR -> {
                    binding?.loadingPb?.visibility = View.GONE
                    binding?.wrongTv?.visibility = View.VISIBLE
                    binding?.popularRc?.visibility = View.GONE

                    binding?.wrongTv?.text = it.message
                }

                Resource.Status.LOADING -> {
                    binding?.loadingPb?.visibility = View.VISIBLE
                    binding?.wrongTv?.visibility = View.GONE
                    binding?.popularRc?.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TopRatedMoviesFragment()
    }

    override fun onClickedMovie(movieId: Double) {
        (activity as MainActivity).navigator.start(MovieDetailFragment.newInstance(movieId))
    }
}