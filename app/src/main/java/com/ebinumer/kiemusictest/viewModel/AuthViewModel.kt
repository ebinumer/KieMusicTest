package com.ebinumer.kiemusictest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebinumer.kiemusictest.data.model.AccessTokenResponse
import com.ebinumer.kiemusictest.data.model.UserProfileResponse
import com.ebinumer.kiemusictest.data.repo.AuthRepo
import kotlinx.coroutines.launch

class AuthViewModel( val authRepo:AuthRepo):ViewModel() {
    private val _accessToken = MutableLiveData<AccessTokenResponse>()
    val accessToken: LiveData<AccessTokenResponse> = _accessToken

    private val _userProfile = MutableLiveData<UserProfileResponse>()
    val userProfile: LiveData<UserProfileResponse> = _userProfile

    fun getAccessToken(code: String, redirectUri: String) {
        viewModelScope.launch {
            try {
                val response = authRepo.getAccessToken(code, redirectUri)
                _accessToken.value = response
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun getUserProfile(token: String) {
        viewModelScope.launch {
            try {
                val response = authRepo.getUserProfile(token)
                _userProfile.value = response
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }
}