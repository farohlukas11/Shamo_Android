package com.faroh.shamoandroid.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    val mediator = MediatorLiveData<Unit>()

    private val _userToken = MutableLiveData<String?>()
    val user: LiveData<String?> = _userToken

    init {
        getUser()
    }

    private fun getUser() {
        val user = shamoUseCase.getUser().asLiveData(Dispatchers.IO)
        mediator.addSource(user) {
            _userToken.value = user.value?.token
        }
    }

    fun getState() = shamoUseCase.getState().asLiveData(Dispatchers.IO)
}