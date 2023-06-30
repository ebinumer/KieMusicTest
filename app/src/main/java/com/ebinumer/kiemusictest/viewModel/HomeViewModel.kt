package com.ebinumer.kiemusictest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebinumer.kiemusictest.data.model.GenreAllResponse
import com.ebinumer.kiemusictest.data.model.SearchResponse
import com.ebinumer.kiemusictest.data.model.TracksResponse
import com.ebinumer.kiemusictest.data.repo.Repository
import com.ebinumer.kiemusictest.data.repo.base.NetworkResult
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mRepoHome: Repository
):ViewModel() {

    private val _allGenreResponse: MutableLiveData<NetworkResult<GenreAllResponse>> = MutableLiveData()
    val allGenreResponse: LiveData<NetworkResult<GenreAllResponse>> = _allGenreResponse

    private val _allRecordingResponse: MutableLiveData<NetworkResult<SearchResponse>> = MutableLiveData()
    val allRecordingResponse: LiveData<NetworkResult<SearchResponse>> = _allRecordingResponse

    private val _popularSongs = MutableLiveData<NetworkResult<TracksResponse>>()
    val popularSongs: LiveData<NetworkResult<TracksResponse>> = _popularSongs


    private fun fetchAllGenreResponse() = viewModelScope.launch {
       Log.e("viewmode","call")
        mRepoHome.getAllGenre().collect { values ->
            _allGenreResponse.value = values
        }
    }

    private fun fetchPopularResponse() = viewModelScope.launch {

        mRepoHome.getRecording("tag:(popular)").collect { values ->
            _allRecordingResponse.value = values
        }
    }
    fun getPopularSongs(country: String, limit: Int) {
        viewModelScope.launch {
            try {
                mRepoHome.getPopularSongs(country, limit).collect{
                    _popularSongs.value = it
                }

            } catch (e: Exception) {

            }
        }
    }

    init {
//        fetchAllGenreResponse()
//        fetchPopularResponse()
        getPopularSongs("US", 10)
    }

}