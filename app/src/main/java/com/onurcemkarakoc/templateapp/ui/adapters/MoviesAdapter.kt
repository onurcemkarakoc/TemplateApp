package com.onurcemkarakoc.templateapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onurcemkarakoc.templateapp.data.models.Result
import com.onurcemkarakoc.templateapp.databinding.MovieItemBinding
import com.onurcemkarakoc.templateapp.ui.viewholders.MoviesViewHolder
import java.util.*

class MoviesAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MoviesViewHolder>() {
    interface MovieItemListener {
        fun onClickedMovie(movieId: Double)
    }

    private val items = ArrayList<Result>()

    fun setItems(items: List<Result>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}


