package com.example.moviemania.Views

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
import com.example.moviemania.Adapters.MovieAdapter
import com.example.moviemania.Models.Movie
import com.example.moviemania.Models.Movies
import com.example.moviemania.R
import com.example.moviemania.ViewModels.FavViewModel
import com.example.moviemania.ViewModels.MovieViewModel


class SearchMoviesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MovieViewModel by viewModels()
    private val favViewModel: FavViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_searchMovie)
        observeSearchResults()
        setListenersForSearch()
        observeResponseText()

    }

    private fun observeResponseText()=viewModel.responseText.observe(viewLifecycleOwner){
        //Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun observeSearchResults() = viewModel.searchReuslt.observe(viewLifecycleOwner) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val adapter = it?.let {  MovieAdapter(it) }
        recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(object :MovieAdapter.onItemClickListener{
            override fun onToggleClick(isFavorite: Boolean, item: Movies) {
                val movie = Movie(movieName = item.original_title, moviePoster = item.poster_path, movieReleaseDate = item.release_date, movieBanner = item.backdrop_path, movieOverview = item.overview)
                if(isFavorite) favViewModel.removeMovie(movie) else favViewModel.insertMovie(movie)
            }

            override fun onItemClick(position: Int) {
                val action = SearchMoviesFragmentDirections.actionSearchMoviesFragmentToMovieDetailFragment(it[position].backdrop_path,it[position].poster_path,it[position].original_title,it[position].release_date,it[position].overview)
                findNavController().navigate(action)
            }
        })
    }

    private fun setListenersForSearch() {

        val etSearchText = view?.findViewById<EditText>(R.id.et_movieSearch)

        etSearchText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length>3){
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