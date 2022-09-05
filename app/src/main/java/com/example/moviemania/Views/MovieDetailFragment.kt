package com.example.moviemania.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.moviemania.Extensions.GlideImageExtension.loadImage
import com.example.moviemania.R

class MovieDetailFragment : Fragment() {

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
        super.onViewCreated(view, savedInstanceState)
     val args =arguments?.let { MovieDetailFragmentArgs.fromBundle(it) }
        view.findViewById<ImageView>(R.id.iv_moviePosterDetailFragment).loadImage(args?.moviePoster)
        view.findViewById<ImageView>(R.id.iv_movieBanner).loadImage(args?.movieBanner)
        view.findViewById<TextView>(R.id.tv_movieNameDetailFragment).text = args?.movieName
        view.findViewById<TextView>(R.id.movieReleaseDateDetailFragment).text = args?.releaseDate
        view.findViewById<TextView>(R.id.movieOverview).text = args?.movieOverview
    }
}