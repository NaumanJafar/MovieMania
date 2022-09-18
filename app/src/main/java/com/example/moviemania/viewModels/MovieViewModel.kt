package com.example.moviemania.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemania.models.Movies
import com.example.moviemania.repositories.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _responseText = MutableLiveData<String>()
    val responseText : LiveData<String>
    get() = _responseText

    private val _movies = MutableLiveData<List<Movies>?>()
    val movies: LiveData<List<Movies>?>
        get() = _movies


    private val _searchResult = MutableLiveData<List<Movies>?>()
    val searchReuslt:LiveData<List<Movies>?>
    get() = _searchResult

    private val repo by lazy { MovieRepository.getInstance() }

    fun apiCall() {
        viewModelScope.launch {
            val response = repo.getMovieResults("e5ea3092880f4f3c25fbc537e9b37dc1")
            _movies.postValue(response.results)
        }
    }

    fun searchApiCall(movieName:String){
        viewModelScope.launch {
            val searchResponse = repo.getMovieSearchResults(movieName)
            if(searchResponse?.results.isNullOrEmpty().not()){
                searchResponse.let {
                    _searchResult.postValue(it?.results)
                }
            }
            else
                _responseText.postValue("No such movie exists")

        }
    }
}