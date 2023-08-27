package com.faroh.shamoandroid.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.faroh.shamoandroid.core.data.source.local.entity.ProductAndGallery
import com.faroh.shamoandroid.core.data.source.local.entity.ProductEntity
import com.faroh.shamoandroid.core.data.source.local.entity.ProductGalleryEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ShamoDao {

    @Transaction
    @Query("SELECT * FROM product")
    fun getAllProductAndGalleries(): Flowable<List<ProductAndGallery>>

    @Transaction
    @Query("SELECT * FROM product WHERE categoryid = :categoryId")
    fun getProductCategories(categoryId: Int): Flowable<List<ProductAndGallery>>

    @Transaction
    @Query("SELECT * FROM product WHERE isFavorite = 1")
    fun getFavouriteProduct(): Flowable<List<ProductAndGallery>>

    @Transaction
    @Query("SELECT * FROM product WHERE inCart > 0")
    fun getCartProduct(): Flowable<List<ProductAndGallery>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(product: List<ProductEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGalleries(galleries: List<ProductGalleryEntity>): Completable

    @Query("UPDATE product SET isFavorite =:state WHERE id =:id")
    fun updateFavouriteProduct(id: Int, state: Int): Completable

    @Query("UPDATE product SET inCart =:state WHERE id =:id")
    fun updateCartProduct(id: Int, state: Int): Completable

    @Query("SELECT count(*) FROM product WHERE id = :id AND isFavorite = 1")
    fun checkFavouriteProduct(id: Int): Flowable<Int>

//    @Query("SELECT SUM(inCart) FROM product WHERE id = :id ")
//    fun getCountProduct(id: Int): Flowable<ProductAndGallery>
}