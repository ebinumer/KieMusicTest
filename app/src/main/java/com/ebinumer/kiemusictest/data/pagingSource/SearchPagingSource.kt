package com.ebinumer.kiemusictest.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ebinumer.kiemusictest.data.api.MusicApi
import com.ebinumer.kiemusictest.data.model.Recordings


class SearchPagingSource(
    private val mRepo: MusicApi,
    private val searchKey:String
    ) : PagingSource<Int, Recordings>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recordings> {
        return try {
            val page = params.key ?: 1
            val response = mRepo.searchRecordings(searchKey,page)

            if (response.isSuccessful) {
                val data = response.body()
                val nextPage = page + 1

                LoadResult.Page(
                    data = data?.recordings ?: emptyList(),
                    prevKey = null,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recordings>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}