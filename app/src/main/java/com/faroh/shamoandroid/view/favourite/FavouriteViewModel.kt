package com.faroh.shamoandroid.view.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getFavouriteProduct() = shamoUseCase.getFavouriteProduct().toLiveData()

    fun deleteFavouriteProduct(dataItem: DataItem) =
        shamoUseCase.setFavouriteProduct(dataItem, false)

}