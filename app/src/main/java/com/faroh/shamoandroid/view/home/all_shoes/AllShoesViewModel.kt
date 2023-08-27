package com.faroh.shamoandroid.view.home.all_shoes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllShoesViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getAllProduct() = shamoUseCase.getAllProduct().toLiveData()
}