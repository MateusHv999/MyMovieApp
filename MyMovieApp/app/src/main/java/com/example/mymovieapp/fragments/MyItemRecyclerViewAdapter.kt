package com.example.mymovieapp.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.example.mymovieapp.placeholder.PlaceholderContent.PlaceholderItem
import com.example.mymovieapp.databinding.FragmentMovieBinding

interface MovieItemListener{
    fun onItemSelected(position: Int)
}

class MyItemRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
    private val listener : MovieItemListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binditem(item)

        holder.view.setOnClickListener {
            listener.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val view = binding.root
        fun binditem(item: PlaceholderItem){
            binding.movieItem = item
            binding.executePendingBindings()
        }
    }

}