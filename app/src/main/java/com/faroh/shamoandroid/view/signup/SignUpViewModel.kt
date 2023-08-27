package com.faroh.shamoandroid.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val shamoUseCase: ShamoUseCase) : ViewModel() {

    fun signUpUser(signUpBody: RegisterBody) =
        shamoUseCase.registerUser(signUpBody).toLiveData()
}