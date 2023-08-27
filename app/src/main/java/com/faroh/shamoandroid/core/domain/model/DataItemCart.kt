package com.faroh.shamoandroid.core.domain.model

import android.os.Parcelable
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItemCart(
    val dataItem: DataItem,
    var inCart: Int = 0
) : Parcelable