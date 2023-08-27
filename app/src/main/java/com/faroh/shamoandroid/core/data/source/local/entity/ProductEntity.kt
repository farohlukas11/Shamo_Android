package com.faroh.shamoandroid.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "categoryid")
    val categoryid: Int,

    @ColumnInfo(name = "categoryName")
    val categoryName: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "inCart")
    var inCart: Int = 0
)
