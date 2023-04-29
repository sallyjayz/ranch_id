package com.sallyjayz.ranchid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.auth.Auth
import com.sallyjayz.ranchid.model.auth.LoginResponse
import com.sallyjayz.ranchid.repository.AuthRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): BaseViewModel() {

    private val _loginResponse = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResponse = _loginResponse

    fun login(auth: Auth, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ) {
        authRepository.login(auth)
    }

    fun isEntryValid(userName: String, password: String): Boolean {
        if (userName.isBlank() || password.isBlank()) {
            return false
        }
        return true
    }
}