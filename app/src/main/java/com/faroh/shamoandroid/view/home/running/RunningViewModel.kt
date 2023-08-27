package com.faroh.shamoandroid.view.home.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunningViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun getProductRunningCategory() = shamoUseCase.getProductCategories(5).toLiveData()

}