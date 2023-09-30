package com.faroh.shamoandroid.view.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) :
    ViewModel() {

    val user = shamoUseCase.getUser().asLiveData(Dispatchers.IO)

    fun getTransaction(token: String) = shamoUseCase.getTransaction(token).toLiveData()
}