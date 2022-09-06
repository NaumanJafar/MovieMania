package com.example.moviemania.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.moviemania.Extensions.GlideImageExtension.loadImage
import com.example.moviemania.R
import com.example.moviemania.ViewModels.FavViewModel

class MovieDetailFragment : Fragment() {
    private val viewModel: FavViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarMovieDetailFragment)
        (activity as MainActivity).setSupportActionBar(toolbar);


        // Display application icon in the toolbar
        (activity as MainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        super.onViewCreated(view, savedInstanceState)
     val args =arguments?.let { MovieDetailFragmentArgs.fromBundle(it) }
        view.findViewById<ImageView>(R.id.iv_moviePosterDetailFragment).loadImage(args?.moviePoster)
        view.findViewById<ImageView>(R.id.iv_movieBanner).loadImage(args?.movieBanner)
        view.findViewById<TextView>(R.id.tv_movieNameDetailFragment).text = args?.movieName
        view.findViewById<TextView>(R.id.movieReleaseDateDetailFragment).text = args?.releaseDate
        view.findViewById<TextView>(R.id.movieOverview).text = args?.movieOverview
        toolbar.title = args?.movieName
        view.findViewById<ImageView>(R.id.iv_ToggleButtonDetailFragment).setImageResource(
            if (args?.favToggle == true){
                R.drawable.redheart
            }
        else{
            R.drawable.redheart_border
            }
        )
        view.findViewById<ImageView>(R.id.iv_ToggleButtonDetailFragment).setOnClickListener{
            if (args?.favToggle ==true){
                it.findViewById<ImageView>(R.id.iv_ToggleButtonDetailFragment).setImageResource(R.drawable.redheart_border)
                args.movie.fav = false
                viewModel.removeMovie(args.movie)
            }
            else if(args?.favToggle==false) {
                it.findViewById<ImageView>(R.id.iv_ToggleButtonDetailFragment).setImageResource(R.drawable.redheart)
                args.movie.fav=true
                viewModel.insertMovie(args.movie)
            }
            }
        }
    }
