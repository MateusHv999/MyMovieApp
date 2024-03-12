package com.example.mymovieapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.databinding.FragmentMovieBinding
import com.example.mymovieapp.responses.Movie

interface MovieItemListener {
    fun onItemSelected(position: Int)
}

class MyItemRecyclerViewAdapter(
    private val listener: MovieItemListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    private var values: List<Movie> = ArrayList()

    fun updateData(movieList: List<Movie>) {
        values = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movieItem = item
            binding.executePendingBindings()
        }
    }
}
