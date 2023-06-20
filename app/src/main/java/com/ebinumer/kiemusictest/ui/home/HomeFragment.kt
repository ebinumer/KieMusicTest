package com.ebinumer.kiemusictest.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.ebinumer.kiemusictest.R
import com.ebinumer.kiemusictest.data.model.Genres
import com.ebinumer.kiemusictest.data.model.Recordings
import com.ebinumer.kiemusictest.data.repo.base.NetworkResult
import com.ebinumer.kiemusictest.databinding.HomeFragmentBinding
import com.ebinumer.kiemusictest.ui.home.adapter.GenreAdapter
import com.ebinumer.kiemusictest.ui.home.adapter.PopularAdapter
import com.ebinumer.kiemusictest.utils.gone
import com.ebinumer.kiemusictest.utils.show
import com.ebinumer.kiemusictest.utils.showToast
import com.ebinumer.kiemusictest.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val mHomeViewModel: HomeViewModel by viewModel()
    lateinit var mBinding: HomeFragmentBinding

    var genres: ArrayList<Genres> = arrayListOf()
    var recordings: ArrayList<Recordings> = arrayListOf()
    lateinit var grnItemsAdapter: GenreAdapter
    lateinit var popularAdapter: PopularAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataObserver()
        initGenre()
        btnClick()
        initPopular()
    }

    private fun btnClick() {
        mBinding.apply {
            imgSearch.setOnClickListener {
                navigateToSearch()
            }
        }
    }


    private fun initGenre() {
        grnItemsAdapter = GenreAdapter(genres) { data: Genres, position: Int ->

        }
        mBinding.rcGenre.apply {
            adapter = grnItemsAdapter
        }
    }
    private fun initPopular() {
        popularAdapter = PopularAdapter(recordings) { data: Recordings, position: Int ->

        }
        mBinding.rcPopularSongs.apply {
            adapter = popularAdapter
        }
    }


    private fun dataObserver() {
        mBinding.apply {
        mHomeViewModel.apply {
            allGenreResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        rcGenre.show()
                        progressMusic.gone()
                        response.data?.genres?.let {
                            genres.addAll(it)
                            grnItemsAdapter.notifyItemInserted(0)
                        }
                    }
                    is NetworkResult.Error -> {
                        rcGenre.show()
                        progressMusic.gone()
                        showToast("Network Error")
                    }

                    is NetworkResult.Loading -> {
                        rcGenre.gone()
                        progressMusic.show()
                    }

                    else -> {
                        rcGenre.show()
                        progressMusic.gone()
                        showToast("Something went wrong")
                    }
                }
            }

            allRecordingResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.recordings?.let {
                            recordings.addAll(it)
                            popularAdapter.notifyItemInserted(0)
                        }
                    }
                    is NetworkResult.Error -> {
                        showToast("Network Error")
                    }

                    is NetworkResult.Loading -> {
                        Log.e("activity", "l")
                    }

                    else -> {
                        showToast("Something went wrong")
                    }
                }
            }
        }
    }}
    private fun navigateToSearch() {
        NavHostFragment.findNavController(this)
            .navigate(HomeFragmentDirections.actionHomeFragmentToSearch())
    }
}