package com.onurcemkarakoc.templateapp.ui.popularmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onurcemkarakoc.templateapp.R
import com.onurcemkarakoc.templateapp.utils.Resource

class PopularMoviesFragment : Fragment() {
    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        popularMoviesViewModel.popularMoviesResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {

                    Toast.makeText(activity, it.data!!.page, Toast.LENGTH_SHORT).show()
//                    bindCharacter(it.data!!)
//                    binding.progressBar.visibility = View.GONE
//                    binding.characterCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    Toast.makeText(activity, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PopularMoviesFragment()
    }
}