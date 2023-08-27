package com.faroh.shamoandroid.view.home.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getProductTrainingCategory() = shamoUseCase.getProductCategories(4).toLiveData()
}