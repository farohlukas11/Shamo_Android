package com.faroh.shamoandroid.view.sigin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun signInUser(signInBody: LoginBody) =
        shamoUseCase.loginUser(signInBody).toLiveData()


    fun saveUser(response: RegisterAndLoginResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            shamoUseCase.saveUser(response)
        }
    }

    fun isLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            shamoUseCase.isLogin()
        }
    }
}