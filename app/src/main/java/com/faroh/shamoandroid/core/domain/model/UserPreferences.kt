package com.faroh.shamoandroid.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPreferences(
    val token: String?,
    val name: String?,
    val username: String?,
    val email: String?,
    val userProfilePhoto: String?
) : Parcelable
