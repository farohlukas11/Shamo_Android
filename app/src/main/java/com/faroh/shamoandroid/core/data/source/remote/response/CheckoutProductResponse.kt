package com.faroh.shamoandroid.core.data.source.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CheckoutProductResponse(

    @field:SerializedName("data")
    val data: DataCheckout? = null,

    @field:SerializedName("meta")
    val meta: Meta? = null
) : Parcelable

@Parcelize
data class DataCheckout(

    @field:SerializedName("shipping_price")
    val shippingPrice: Int? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("total_price")
    val totalPrice: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("users_id")
    val usersId: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("items")
    val items: List<ItemsCheckout?>? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@Parcelize
data class ProductCheckout(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("categories_id")
    val categoriesId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: String? = null,

    @field:SerializedName("tags")
    val tags: String? = null
) : Parcelable

@Parcelize
data class ItemsCheckout(

    @field:SerializedName("transactions_id")
    val transactionsId: Int? = null,

    @field:SerializedName("product")
    val product: ProductCheckout? = null,

    @field:SerializedName("products_id")
    val productsId: Int? = null,

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("users_id")
    val usersId: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable
