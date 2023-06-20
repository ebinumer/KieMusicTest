package com.ebinumer.kiemusictest.ui.search

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ebinumer.kiemusictest.R
import com.ebinumer.kiemusictest.data.model.Genres
import com.ebinumer.kiemusictest.data.model.Recordings
import com.ebinumer.kiemusictest.data.repo.base.NetworkResult
import com.ebinumer.kiemusictest.data.roomDb.SearchItem
import com.ebinumer.kiemusictest.databinding.SearchFragmentBinding
import com.ebinumer.kiemusictest.ui.home.adapter.GenreAdapter
import com.ebinumer.kiemusictest.ui.search.adapter.SearchHistoryAdapter
import com.ebinumer.kiemusictest.utils.gone
import com.ebinumer.kiemusictest.utils.show
import com.ebinumer.kiemusictest.utils.showToast
import com.ebinumer.kiemusictest.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment:Fragment() {
    private val mViewModel:SearchViewModel by viewModel()
    lateinit var mBinding:SearchFragmentBinding
    lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private var searchItems: ArrayList<SearchItem> = arrayListOf()
    var genres: ArrayList<Genres> = arrayListOf()
    lateinit var grnItemsAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.search_fragment,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        dataObserver()
        setSearchHistory()
        initGenre()
    }


    private fun setSearchHistory() {
        searchHistoryAdapter = SearchHistoryAdapter(searchItems) { searchItem, i ->
            navigateToSearchResult(searchItem.title)
        }
        mBinding.apply {

            rcSearchHistory.apply {
                adapter = searchHistoryAdapter
            }
        }
    }
    private fun initGenre() {
        grnItemsAdapter = GenreAdapter(genres) { data: Genres, position: Int ->

        }
        mBinding.rcPopularGen.apply {
            adapter = grnItemsAdapter
        }
    }

    private fun initUi() {

        mBinding.apply {
            edtSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        navigateToSearchResult(textView.text.toString())
                        textView.text =""
                        true

                    }

                    EditorInfo.IME_ACTION_DONE -> {
                        Log.e(ContentValues.TAG, "initUi:done ")
                        true
                    }

                    EditorInfo.IME_ACTION_SEND -> {
                        Log.e(ContentValues.TAG, "initUi:send ")
                        true
                    }

                    else -> false // Return false if action not handled
                }
            }
            btnClear.setOnClickListener {
                mViewModel.deleteHistory()

            }


        }


    }


    private fun dataObserver() {
        mViewModel.apply {
            searchHistoryResults.observe(viewLifecycleOwner) { historyData ->
                historyData?.let {
                    mBinding.apply {
                        searchItems.clear()
                        searchItems.addAll(it)
                        searchHistoryAdapter.notifyItemInserted(0)
                        if (it.isEmpty()) {
                            noSearchTxt.show()
                        } else {
                            noSearchTxt.gone()
                        }
                    }
                }

            }

            historyDeleteStatus.observe(viewLifecycleOwner) { success ->
                if (success) {
                    searchHistoryAdapter.notifyDataSetChanged()
                    showToast("Data deleted successfully")
                } else {
                    showToast("Failed to delete data")
                }
            }

            allGenreResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.genres?.let {
                            genres.addAll(it)
                            grnItemsAdapter.notifyItemInserted(0)
                        }
                    }
                    is NetworkResult.Error -> {
                        showToast("Network Error")
                    }

                    is NetworkResult.Loading -> {
                    }

                    else -> {
                        showToast("Something went wrong")
                    }
                }
            }
        }
    }

    private fun navigateToSearchResult(searchText: String) {
    NavHostFragment.findNavController(this)
        .navigate(SearchFragmentDirections.actionSearchFragmentToSearchResult(searchText))

}


}