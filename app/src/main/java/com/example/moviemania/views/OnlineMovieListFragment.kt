package com.example.moviemania.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.adapters.MovieAdapter
import com.example.moviemania.models.Movie
import com.example.moviemania.models.Movies
import com.example.moviemania.R
import com.example.moviemania.databinding.FragmentOnlineMovieListBinding
import com.example.moviemania.viewModels.FavViewModel
import com.example.moviemania.viewModels.MovieViewModel


class OnlineMovieListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MovieViewModel by viewModels()
    private val favViewModel: FavViewModel by viewModels()
    private lateinit var binding: FragmentOnlineMovieListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        // Inflate the layout for this fragment
        binding = FragmentOnlineMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView =binding.rvOnlineMovieFragment
        val toolbar =
            binding.toolbarPopluarMoviesFragment
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).onBackPressed()
        }


        binding.searchIcon.setOnClickListener {
            (activity as MainActivity).navigateToSearchMoviesFragment()
        }
        observeMovies()
        viewModel.apiCall()
        binding.btnFavourites.setOnClickListener {
            (activity as MainActivity).navigateToFavoritesFragment()
        }


    }

    private fun observeMovies() = viewModel.movies.observe(viewLifecycleOwner) {
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)
        val adapter = it?.let { MovieAdapter(it) }
        recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(object : MovieAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val movie = Movie(
                    movieName = it[position].original_title,
                    moviePoster = it[position].poster_path,
                    movieReleaseDate = it[position].release_date,
                    movieBanner = it[position].backdrop_path,
                    movieOverview = it[position].overview,
                    fav = it[position].fav
                )
                val action =
                    OnlineMovieListFragmentDirections.actionOnlineMovieListFragmentToMovieDetailFragment(
                        it[position].fav,
                        movie,
                        it[position].backdrop_path,
                        it[position].poster_path,
                        it[position].original_title,
                        it[position].release_date,
                        it[position].overview


                    )
                findNavController().navigate(action)
            }

            override fun onToggleClick(isFavorite: Boolean, item: Movies) {
                val movie = Movie(
                    movieName = item.original_title,
                    moviePoster = item.poster_path,
                    movieReleaseDate = item.release_date,
                    movieBanner = item.backdrop_path,
                    movieOverview = item.overview
                )
                if (isFavorite) favViewModel.removeMovie(movie) else favViewModel.insertMovie(movie)
            }

        })
    }


}