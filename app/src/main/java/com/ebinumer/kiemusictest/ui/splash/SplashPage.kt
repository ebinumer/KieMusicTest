package com.ebinumer.kiemusictest.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ebinumer.kiemusictest.R
import com.ebinumer.kiemusictest.databinding.SplashPageBinding
import com.ebinumer.kiemusictest.utils.SessionManager
import com.ebinumer.kiemusictest.viewModel.AuthViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Queue

class SplashPage : Fragment() {
    lateinit var mBinding: SplashPageBinding
    private val mViewModel: AuthViewModel by viewModel()
    private val mSessionManager by inject<SessionManager>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.splash_page, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        observer()
    }

    private fun observer() {
        mViewModel.accessToken.observe(viewLifecycleOwner) { accessToken ->
            mSessionManager.token = accessToken.accessToken
            navToHome()
        }

        mViewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
            // Handle user profile response
        }
    }

    private fun callApi() {
        val code = extractAuthorizationCodeFromCallback()
        mViewModel.getAccessToken(code.toString(), "http://localhost/")
//        mViewModel.getUserProfile("YOUR_ACCESS_TOKEN")
    }

    // Helper method to extract the authorization code from the callback URL
    private fun extractAuthorizationCodeFromCallback(): String? {
//        val uri = intent.data
//        if (uri != null && uri.scheme == "YOUR_REDIRECT_URI_SCHEME") {
//            return uri.getQueryParameter("code")
//        }
        return null
    }

    private fun navToHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            NavHostFragment.findNavController(this).apply {
                navigate(SplashPageDirections.actionSplashPageToHomeFragment())
            }
        }, 1000)

    }


}