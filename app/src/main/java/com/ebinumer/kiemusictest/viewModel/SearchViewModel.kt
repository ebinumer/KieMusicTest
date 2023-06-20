package com.ebinumer.kiemusictest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ebinumer.kiemusictest.data.model.GenreAllResponse
import com.ebinumer.kiemusictest.data.model.Recordings
import com.ebinumer.kiemusictest.data.model.SearchResponse
import com.ebinumer.kiemusictest.data.repo.Repository
import com.ebinumer.kiemusictest.data.repo.base.NetworkResult
import com.ebinumer.kiemusictest.data.roomDb.SearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SearchViewModel(private val mRepoHome: Repository) : ViewModel() {

    private val _searchResponse: MutableLiveData<NetworkResult<SearchResponse>> = MutableLiveData()
    val searchResponse: LiveData<NetworkResult<SearchResponse>> = _searchResponse

    private val _searchHistoryResults = MutableLiveData<List<SearchItem>>(emptyList())
    val searchHistoryResults: LiveData<List<SearchItem>> = _searchHistoryResults

    private val _historyDeleteStatus = MutableLiveData<Boolean>()
    val historyDeleteStatus: LiveData<Boolean>
        get() = _historyDeleteStatus

    private val _allGenreResponse: MutableLiveData<NetworkResult<GenreAllResponse>> = MutableLiveData()
    val allGenreResponse: LiveData<NetworkResult<GenreAllResponse>> = _allGenreResponse


    suspend fun getSearchResponse(searchString: String): Flow<PagingData<Recordings>> {
        return mRepoHome.searchItems(searchString).flowOn(Dispatchers.IO)
    }


    fun getSearchHistory() {
        viewModelScope.launch {
            mRepoHome.searchHistory()
                .collectLatest { searchHistory ->
                    _searchHistoryResults.value = searchHistory
                }
        }
    }

    fun addToHistory(searchItem: SearchItem) {
        viewModelScope.launch {
            mRepoHome.addToHistory(searchItem)
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            mRepoHome.deleteHistory().let { success ->
                _historyDeleteStatus.postValue(success)
            }
        }
    }
    private fun fetchAllGenreResponse() = viewModelScope.launch {
        Log.e("viewmode","call")
        mRepoHome.getAllGenre().collect { values ->
            _allGenreResponse.value = values
        }
    }



    init {
        getSearchHistory()
        fetchAllGenreResponse()
    }
}