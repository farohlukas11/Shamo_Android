package com.faroh.shamoandroid.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    private var _dataItem: MutableLiveData<DataItem> = MutableLiveData()

    val dataItem: LiveData<DataItem> = _dataItem

    fun saveDataItem(data: DataItem) {
        _dataItem.value = data
    }

    fun getFamiliarProductByCategory() =
        shamoUseCase.getProductCategories(_dataItem.value?.categoriesId!!).toLiveData()

    fun setCartProduct() = shamoUseCase.setCartProduct(DataItemCart(_dataItem.value!!, 1))

    fun setFavouriteProduct() = shamoUseCase.setFavouriteProduct(_dataItem.value!!, true)

    fun deleteFavouriteProduct() = shamoUseCase.setFavouriteProduct(_dataItem.value!!, false)

    fun checkFavouriteProduct() =
        shamoUseCase.checkFavouriteProduct(_dataItem.value?.id!!).toLiveData()

}