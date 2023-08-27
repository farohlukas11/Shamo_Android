package com.faroh.shamoandroid.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery")
data class ProductGalleryEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "url")
    val imageUrl: String,

    @ColumnInfo(name = "prodId")
    val prodId: Int
)
