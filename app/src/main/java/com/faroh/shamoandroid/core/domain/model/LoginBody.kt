package com.faroh.shamoandroid.core.domain.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class LoginBody(
    val email: String,
    val password: String
) : Parcelable
