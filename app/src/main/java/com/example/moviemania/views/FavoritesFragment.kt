package com.example.moviemania.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.adapters.FavAdapter
import com.example.moviemania.models.Movie
import com.example.moviemania.R
import com.example.moviemania.databinding.FragmentFavoritesBinding
import com.example.moviemania.viewModels.FavViewModel

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: FavViewModel by viewModels()
    private lateinit var binding:FragmentFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar =
            binding.toolbarFavoritesFragment
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvFavourites
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