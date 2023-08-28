package com.faroh.shamoandroid.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataCheckout(
    val address: String? = "Kota Madiun",
    val items: List<ItemCheckOut>? = null,
    val status: String = "PENDING",
    val total_price: Int? = 0,
    val shipping_price: Int? = 0
) : Parcelable

@Parcelize
data class ItemCheckOut(
    val id: Int? = null,
    val quantity: Int? = null
) : Parcelable
