package com.sallyjayz.ranchid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sallyjayz.ranchid.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager
): ViewModel() {

    val username = MutableLiveData<String?>()
    val token = MutableLiveData<String?>()
    val name = MutableLiveData<String?>()
    val userEmail = MutableLiveData<String?>()
    val userRole = MutableLiveData<String?>()
//    val userPhoto = MutableLiveData<String?>()
    val vetCouncilNumber = MutableLiveData<String?>()

    private val _loginRole = MutableLiveData<String>()
    val loginRole : LiveData<String> = _loginRole

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getToken().collect {
                withContext(Dispatchers.Main) {
                    token.value = it
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getUsername().collect {
                withContext(Dispatchers.Main) {
                    username.value = it
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getName().collect {
                withContext(Dispatchers.Main) {
                    name.value = it
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getEmail().collect {
                withContext(Dispatchers.Main) {
                    userEmail.value = it
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getRole().collect {
                withContext(Dispatchers.Main) {
                    userRole.value = it
                }
            }
        }

        /*viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getPhoto().collect {
                withContext(Dispatchers.Main) {
                    userPhoto.value = it
                }
            }
        }*/

        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getVetCouncilNumber().collect {
                withContext(Dispatchers.Main) {
                    vetCouncilNumber.value = it
                }
            }
        }

    }

    fun saveToken(token: String, username: String, name: String, email: String, role: String/*,
                  photo: String*/) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveToken(token, username, name, email, role/*, photo*/)
        }
    }

    fun saveVetCouncilNumber(vetCouncilNumber: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveVetCouncilNumber(vetCouncilNumber)
        }
    }

    fun setLoginRole(loginRole: String) {
        _loginRole.value = loginRole
    }


    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteToken()
        }
    }

    fun deleteVetCouncilNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteVetCouncilNumber()
        }
    }
}