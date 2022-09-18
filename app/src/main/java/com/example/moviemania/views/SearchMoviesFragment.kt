package com.example.moviemania.views

import android.os.Binder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.adapters.MovieAdapter
import com.example.moviemania.models.Movie
import com.example.moviemania.models.Movies
import com.example.moviemania.R
import com.example.moviemania.databinding.FragmentSearchMoviesBinding
import com.example.moviemania.viewModels.FavViewModel
import com.example.moviemania.viewModels.MovieViewModel


class SearchMoviesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MovieViewModel by viewModels()
    private val favViewModel: FavViewModel by viewModels()
    private lateinit var binding:FragmentSearchMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val toolbar = binding.toolbarSearchMoviesFragment
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvSearchMovie
        observeSearchResults()
        setListenersForSearch()
        observeResponseText()

    }

    private fun observeResponseText() = viewModel.responseText.observe(viewLifecycleOwner) {
        //Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        val list:List<Movies>?=null
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        val adapter = list?.let { it -> MovieAdapter(it) }
        recyclerView.adapter=adapter
    }

    private fun observeSearchResults() = viewModel.searchReuslt.observe(viewLifecycleOwner) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = it?.let { MovieAdapter(it) }
        recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(object : MovieAdapter.onItemClickListener {
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
                    SearchMoviesFragmentDirections.actionSearchMoviesFragmentToMovieDetailFragment(
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
        })
    }

    private fun setListenersForSearch() {

        val etSearchText = view?.findViewById<EditText>(R.id.et_movieSearch)

        etSearchText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length > 3) {
                    viewModel.searchApiCall(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


}