package com.onurcemkarakoc.templateapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.onurcemkarakoc.templateapp.data.models.Result
import com.onurcemkarakoc.templateapp.databinding.MovieItemBinding
import com.onurcemkarakoc.templateapp.ui.viewholders.MoviesViewHolder

class MoviesAdapter(private val listener: MovieItemListener) :
    PagingDataAdapter<Result, MoviesViewHolder>(RESULT_COMPARATOR) {
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    companion object {
        private val RESULT_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem == newItem

        }
    }

    interface MovieItemListener {
        fun onClickedMovie(movieId: Double)
    }

}