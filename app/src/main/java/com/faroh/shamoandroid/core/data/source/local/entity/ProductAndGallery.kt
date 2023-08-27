package com.faroh.shamoandroid.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProductAndGallery(
    @Embedded
    val product: ProductEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "prodId"
    )

    val galleries: List<ProductGalleryEntity>
)
