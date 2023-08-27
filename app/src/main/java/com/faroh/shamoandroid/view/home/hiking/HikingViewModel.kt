package com.faroh.shamoandroid.view.home.hiking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HikingViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {
    fun getProductHikingCategory() = shamoUseCase.getProductCategories(2).toLiveData()

}