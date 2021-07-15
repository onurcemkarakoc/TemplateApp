package com.onurcemkarakoc.templateapp.ui.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.onurcemkarakoc.templateapp.BuildConfig
import com.onurcemkarakoc.templateapp.data.models.MovieDetailResponse
import com.onurcemkarakoc.templateapp.databinding.FragmentMovieDetailBinding
import com.onurcemkarakoc.templateapp.utils.Constants
import com.onurcemkarakoc.templateapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var movieId: Double? = null
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private var binding: FragmentMovieDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getDouble(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId?.let {
            movieDetailViewModel.start(it)
            setupObservers()
        }
    }

    private fun setupObservers() {
        movieDetailViewModel.detail.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding?.loadingPb?.visibility = View.GONE
                    if (it.data == null) {
                        binding?.wrongTv?.text = it.message ?: "Hata"
                        binding?.wrongTv?.visibility = View.VISIBLE
                        binding?.rootSv?.visibility = View.GONE
                    } else {
                        binding?.rootSv?.visibility = View.VISIBLE
                        binding?.wrongTv?.visibility = View.GONE
                        drawUi(it.data)
                    }
                }

                Resource.Status.ERROR -> {
                    binding?.wrongTv?.text = it.message ?: "Hata"
                    binding?.wrongTv?.visibility = View.VISIBLE
                    binding?.loadingPb?.visibility = View.GONE
                    binding?.rootSv?.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    binding?.loadingPb?.visibility = View.VISIBLE
                    binding?.rootSv?.visibility = View.GONE
                    binding?.wrongTv?.visibility = View.GONE
                }
            }
        }
    }

    private fun drawUi(response: MovieDetailResponse) {

        if (response.backdrop_path.isBlank()) {
            binding?.movieCoverIv?.visibility = View.GONE
        } else {
            Glide.with(requireContext())
                .load(BuildConfig.IMAGE_BASE_URL + Constants.W500 + response.backdrop_path)
                .centerCrop()
                .into(binding?.movieCoverIv!!)
        }

        if (response.poster_path.isBlank()) {
            binding?.posterIv?.visibility = View.GONE
        } else {
            Glide.with(requireContext())
                .load(BuildConfig.IMAGE_BASE_URL + Constants.W500 + response.poster_path)
                .centerCrop()
                .into(binding?.posterIv!!)
        }

        if (response.original_title.isBlank()) {
            binding?.movieNameTv?.visibility = View.GONE
        } else {
            binding?.movieNameTv?.text = response.original_title
        }

        if (response.release_date.isBlank()) {
            binding?.releaseDateTv?.visibility = View.GONE
        } else {
            binding?.releaseDateTv?.text = "${response.release_date} (${response.status})"
        }

        binding?.backBtn?.setOnClickListener {
            requireActivity().onBackPressed()
        }

        if (response.tagline.isBlank()) {
            binding?.tagLineTv?.visibility = View.GONE
        } else {
            binding?.tagLineTv?.text = response.tagline
        }

        if (response.homepage.isBlank()) {
            binding?.homePageBtn?.visibility = View.GONE
        } else {
            binding?.homePageBtn?.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(response.homepage)
                startActivity(i)
            }
        }

        if (response.popularity.isNaN()) {
            binding?.popularityLl?.visibility = View.GONE
        } else {
            binding?.popularityTv?.text = response.popularity.toString().split(".")[0] + "k"
        }
        if (response.vote_average.isNaN()) {
            binding?.voteLl?.visibility = View.GONE
        } else {
            binding?.voteTv?.text = response.vote_average.toString()
        }
        if (response.genres[0].name.isBlank()) {
            binding?.genreLl?.visibility = View.GONE
        } else {
            binding?.genreTv?.text = response.genres[0].name
        }
        if (response.original_language.isBlank()) {
            binding?.languageLl?.visibility = View.GONE
        } else {
            binding?.languageTv?.text = response.original_language
        }
        if (response.overview.isBlank()) {
            binding?.overviewTv?.visibility = View.GONE
        } else {
            binding?.overviewTv?.text = response.overview
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Double) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PARAM1, param1)
                }
            }
    }
}