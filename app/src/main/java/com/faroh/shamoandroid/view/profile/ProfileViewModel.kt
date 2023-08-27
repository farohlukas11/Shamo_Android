package com.faroh.shamoandroid.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getUser() = shamoUseCase.getUser().asLiveData(Dispatchers.IO)

    fun logoutUser(token: String) = shamoUseCase.logoutUser(token).toLiveData()

    fun saveUser(response: RegisterAndLoginResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            shamoUseCase.saveUser(response)
        }
    }

    fun isLogOut() {
        viewModelScope.launch(Dispatchers.IO) {
            shamoUseCase.isLogout()
        }
    }

}