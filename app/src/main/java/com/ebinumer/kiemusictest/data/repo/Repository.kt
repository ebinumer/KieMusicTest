package com.ebinumer.kiemusictest.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ebinumer.kiemusictest.data.api.MusicApi
import com.ebinumer.kiemusictest.data.model.GenreAllResponse
import com.ebinumer.kiemusictest.data.model.Recordings
import com.ebinumer.kiemusictest.data.model.SearchResponse
import com.ebinumer.kiemusictest.data.repo.base.BaseApiResponse
import com.ebinumer.kiemusictest.data.repo.base.NetworkResult
import com.ebinumer.kiemusictest.data.roomDb.SearchDao
import com.ebinumer.kiemusictest.data.roomDb.SearchItem
import com.ebinumer.kiemusictest.data.pagingSource.SearchPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(
    private val mApi: MusicApi,
    private val searchDao: SearchDao,
): BaseApiResponse() {

    suspend fun getAllGenre():Flow<NetworkResult<GenreAllResponse>> {
        return flow {
            emit(safeApiCall { mApi.getAllGenre() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRecording(query:String):Flow<NetworkResult<SearchResponse>> {
        return flow {
            emit(safeApiCall { mApi.searchRecordings(query) })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun searchItems(searchString:String): Flow<PagingData<Recordings>> {
        return Pager(PagingConfig(pageSize = 20)) {
            SearchPagingSource(mApi,searchString)
        }.flow
    }

    suspend fun searchHistory(): Flow<List<SearchItem>> {
        return searchDao.getRecentSearches()
    }

    suspend fun addToHistory(searchItem: SearchItem) {
         searchDao.saveSearchItem(searchItem)
    }

    suspend fun deleteHistory(): Boolean {
        return try {
            searchDao.deleteAll()
            true
        } catch (e: Exception) {
            false
        }

    }
}