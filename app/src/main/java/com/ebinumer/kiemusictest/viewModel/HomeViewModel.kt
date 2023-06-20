package com.ebinumer.kiemusictest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebinumer.kiemusictest.data.model.GenreAllResponse
import com.ebinumer.kiemusictest.data.model.SearchResponse
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


    init {
        fetchAllGenreResponse()
        fetchPopularResponse()
    }

}