package com.example.moviemania.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.moviemania.extensions.GlideImageExtension.loadImage
import com.example.moviemania.R
import com.example.moviemania.databinding.FragmentMovieDetailBinding
import com.example.moviemania.viewModels.FavViewModel

class MovieDetailFragment : Fragment() {
    private val viewModel: FavViewModel by viewModels()
    private lateinit var binding:FragmentMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val toolbar =binding.toolbarMovieDetailFragment
        (activity as MainActivity).setSupportActionBar(toolbar);


        // Display application icon in the toolbar
        (activity as MainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        super.onViewCreated(view, savedInstanceState)
     val args =arguments?.let { MovieDetailFragmentArgs.fromBundle(it) }
        binding.ivMoviePosterDetailFragment.loadImage(args?.moviePoster)
        binding.ivMovieBanner.loadImage(args?.movieBanner)
        binding.tvMovieNameDetailFragment.text = args?.movieName
        binding.movieReleaseDateDetailFragment.text = args?.releaseDate
        binding.movieOverview.text = args?.movieOverview
        toolbar.title = args?.movieName
        val btnToggle = binding.ivToggleButtonDetailFragment
        btnToggle.setImageResource(
            if (args?.favToggle == true){
                R.drawable.redheart
            }
        else{
            R.drawable.redheart_border
            }
        )
        btnToggle.setOnClickListener{
            if (args?.favToggle ==true){
                btnToggle.setImageResource(R.drawable.redheart_border)
                args.movie.fav = false
                viewModel.removeMovie(args.movie)
            }
            else if(args?.favToggle==false) {
              btnToggle.setImageResource(R.drawable.redheart)
                args.movie.fav=true
                viewModel.insertMovie(args.movie)
            }
            }
        }
    }
