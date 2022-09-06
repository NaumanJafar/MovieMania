package com.example.moviemania.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.Adapters.FavAdapter
import com.example.moviemania.Adapters.MovieAdapter
import com.example.moviemania.Models.Movie
import com.example.moviemania.R
import com.example.moviemania.ViewModels.FavViewModel

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: FavViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar =
            view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarFavoritesFragment)
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_favourites)
        viewModel.getAllFavMovies()
        observeFavoritesResults()


    }

    private fun observeFavoritesResults() = viewModel.favList.observe(viewLifecycleOwner) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = it?.let { FavAdapter(it) }
        recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(object : FavAdapter.onItemClickListener {
            override fun onToggleClick(isFavorite: Boolean, item: Movie) {
                if (isFavorite) viewModel.removeMovie(item) else viewModel.insertMovie(item)
            }

            override fun onItemClick(position: Int) {

                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailFragment(
                        it[position].fav,
                        it[position],
                        it[position].movieBanner,
                        it[position].moviePoster,
                        it[position].movieName,
                        it[position].movieReleaseDate,
                        it[position].movieOverview
                    )
                findNavController().navigate(action)
            }
        })
    }

}