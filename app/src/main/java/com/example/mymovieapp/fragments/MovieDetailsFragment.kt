package com.example.mymovieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.example.mymovieapp.MovieViewModel
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentMovieDetailsBinding
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class MovieDetailsFragment : Fragment() {
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_nav){defaultViewModelProviderFactory}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentMovieDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movie_details, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}