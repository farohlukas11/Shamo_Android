package com.faroh.shamoandroid.view.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getCartProduct() = shamoUseCase.getCartProduct().toLiveData()

    fun setCartProduct(dataItem: DataItemCart) =
        shamoUseCase.setCartProduct(dataItem)

//    fun getCountProduct(id: Int) = shamoUseCase.getCountProduct(id).toLiveData()
}