package com.faroh.shamoandroid.view.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.model.DataCheckout
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getCartProduct() = shamoUseCase.getCartProduct().toLiveData()

    val user = shamoUseCase.getUser().asLiveData(Dispatchers.IO)

    fun checkoutProduct(token: String, dataCheckout: DataCheckout) =
        shamoUseCase.checkoutProduct(token, dataCheckout).toLiveData()
}