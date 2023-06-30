package com.ebinumer.kiemusictest.ui.search

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.ebinumer.kiemusictest.R
import com.ebinumer.kiemusictest.data.model.Recordings
import com.ebinumer.kiemusictest.data.roomDb.SearchItem
import com.ebinumer.kiemusictest.databinding.SearchResultFragmentBinding
import com.ebinumer.kiemusictest.ui.search.adapter.SearchAdapter
import com.ebinumer.kiemusictest.utils.showToast
import com.ebinumer.kiemusictest.viewModel.SearchViewModel
import com.google.android.gms.tflite.support.Empty
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchResultFragment:Fragment() {
    private val searchResultViewModel : SearchViewModel  by viewModel()
    private lateinit var mBinding:SearchResultFragmentBinding
    lateinit var searchingAdapter: SearchAdapter
    var recordings: ArrayList<Recordings> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.search_result_fragment,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromIntent()
        initSearchData()
    }

    private fun initSearchData() {
        searchingAdapter = SearchAdapter( { data: Recordings, position: Int ->
            showToast("Clicked on ${data.title}")

            searchResultViewModel.addToHistory(SearchItem(title = data.title.toString(), itemId = data.id, type = "Song"))
        },{data, position ->
            searchResultViewModel.fetchSong(data.id.toString())
        })
        mBinding.rcSearchResult.apply {
            adapter = searchingAdapter
        }
    }

    private fun getDataFromIntent() {
        mBinding.apply {
            val argumentValue = arguments?.getString("search")
            argumentValue?.let {

                txtSearch.text = "\"$it\""

                lifecycleScope.launch {
                    searchResultViewModel.apply {

                        getSearchResponse(it).collectLatest { value: PagingData<Recordings> ->

                            addToHistory(SearchItem(title = it))
                            searchingAdapter.submitData(lifecycle, value)

                        }
                    }
                }
            }
        }

}
}