package com.onurcemkarakoc.templateapp.ui.topratedmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onurcemkarakoc.templateapp.databinding.FragmentTopRatedMoviesBinding
import com.onurcemkarakoc.templateapp.ui.MainActivity
import com.onurcemkarakoc.templateapp.ui.adapters.MoviesAdapter
import com.onurcemkarakoc.templateapp.ui.moviedetail.MovieDetailFragment
import com.onurcemkarakoc.templateapp.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment(), MoviesAdapter.MovieItemListener {
    private val topRatedMoviesViewModel: TopRatedMovieViewModel by viewModels()
    private var binding: FragmentTopRatedMoviesBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter
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
        moviesAdapter = MoviesAdapter(this)
        val layoutManager = object : GridLayoutManager(context, 2, VERTICAL, false) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                lp.width = (width / spanCount) - 32
                lp.height = ((width / spanCount) * 3 / 2) - 32
                return true
            }
        }
        binding?.topRatedRc?.layoutManager = layoutManager
        binding?.topRatedRc?.addItemDecoration(SpacesItemDecoration(2, 8f, 8f, 8f, 8f))
        binding?.topRatedRc?.adapter = moviesAdapter

        moviesAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                LoadState.Loading -> {
                    binding?.loadingPb?.visibility = View.VISIBLE
                    binding?.wrongTv?.visibility = View.GONE
                    binding?.topRatedRc?.visibility = View.GONE
                }
                else -> {
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    if (error == null) {
                        binding?.loadingPb?.visibility = View.GONE
                        binding?.wrongTv?.visibility = View.GONE
                        binding?.topRatedRc?.visibility = View.VISIBLE
                    } else {
                        if (moviesAdapter.itemCount > 0) {
                            Toast.makeText(activity, "No data!", Toast.LENGTH_SHORT).show()
                        } else {
                            binding?.loadingPb?.visibility = View.GONE
                            binding?.wrongTv?.visibility = View.VISIBLE
                            binding?.topRatedRc?.visibility = View.GONE

                            binding?.wrongTv?.text = error.error.message
                        }

                    }
                }
            }

        }

    }

    private fun setupObservers() {
        topRatedMoviesViewModel.topRatedMoviesResponse.observe(viewLifecycleOwner) {
            moviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
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