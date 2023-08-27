package com.faroh.shamoandroid.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getUser() = shamoUseCase.getUser().asLiveData(Dispatchers.IO)

}