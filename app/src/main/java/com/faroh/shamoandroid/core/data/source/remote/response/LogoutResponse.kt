package com.faroh.shamoandroid.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@kotlinx.parcelize.Parcelize
data class LogoutResponse(

    @field:SerializedName("data")
    val data: Boolean? = null,

    @field:SerializedName("meta")
    val meta: Meta? = null
) : Parcelable

