package com.example.mymovieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.mymovieapp.MovieViewModel
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentMovieListBinding
import com.example.mymovieapp.placeholder.PlaceholderContent

class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyItemRecyclerViewAdapter
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_nav){defaultViewModelProviderFactory}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val recyclerView = binding.list

        adapter = MyItemRecyclerViewAdapter(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MovieFragment.adapter
        }

        initObservers()

        return binding.root
    }
    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }

    private fun initObservers() {
        viewModel.movieListLivedata.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })
        viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment()
            findNavController().navigate(action)
        })
    }
}