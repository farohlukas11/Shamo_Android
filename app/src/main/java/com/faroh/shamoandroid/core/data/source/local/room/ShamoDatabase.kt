package com.faroh.shamoandroid.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faroh.shamoandroid.core.data.source.local.entity.ProductEntity
import com.faroh.shamoandroid.core.data.source.local.entity.ProductGalleryEntity

@Database(
    entities = [ProductEntity::class, ProductGalleryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShamoDatabase : RoomDatabase() {
    abstract fun shamoDao(): ShamoDao
}