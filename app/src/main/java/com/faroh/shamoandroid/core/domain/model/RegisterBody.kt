package com.faroh.shamoandroid.core.domain.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
class RegisterBody(
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val phone: String
) : Parcelable