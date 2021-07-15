package com.onurcemkarakoc.templateapp.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onurcemkarakoc.templateapp.BuildConfig
import com.onurcemkarakoc.templateapp.R
import com.onurcemkarakoc.templateapp.data.models.Result
import com.onurcemkarakoc.templateapp.databinding.MovieItemBinding
import com.onurcemkarakoc.templateapp.ui.adapters.MoviesAdapter
import com.onurcemkarakoc.templateapp.utils.Constants

class MoviesViewHolder(
    private val binding: MovieItemBinding,
    private val listener: MoviesAdapter.MovieItemListener
) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var result: Result
    fun bind(result: Result) {
        this.result = result
        Glide.with(binding.root)
            .load(BuildConfig.IMAGE_BASE_URL + Constants.W500 + result.poster_path)
            .placeholder(R.drawable.ic_baseline_fireplace_24)
            .fitCenter()
            .into(binding.movieItemImg)
        binding.root.setOnClickListener {
            listener.onClickedMovie(result.id)
        }
    }

}