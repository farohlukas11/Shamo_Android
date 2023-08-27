package com.faroh.shamoandroid.view.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getCartProduct() = shamoUseCase.getCartProduct().toLiveData()
}