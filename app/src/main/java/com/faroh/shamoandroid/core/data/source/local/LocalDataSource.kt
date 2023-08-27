package com.faroh.shamoandroid.core.data.source.local

import com.faroh.shamoandroid.core.data.source.local.entity.ProductAndGallery
import com.faroh.shamoandroid.core.data.source.local.entity.ProductEntity
import com.faroh.shamoandroid.core.data.source.local.entity.ProductGalleryEntity
import com.faroh.shamoandroid.core.data.source.local.room.ShamoDao
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val shamoDao: ShamoDao) {

    fun getAllProductAndGalleries(): Flowable<List<ProductAndGallery>> =
        shamoDao.getAllProductAndGalleries()

    fun getProductCategories(categoryId: Int): Flowable<List<ProductAndGallery>> =
        shamoDao.getProductCategories(categoryId)

    fun getFavouriteProduct(): Flowable<List<ProductAndGallery>> = shamoDao.getFavouriteProduct()

    fun getCartProduct(): Flowable<List<ProductAndGallery>> = shamoDao.getCartProduct()

    fun insertProduct(product: List<ProductEntity>) = shamoDao.insertProduct(product)

    fun insertGalleries(galleries: List<ProductGalleryEntity>) = shamoDao.insertGalleries(galleries)

    fun setFavouriteProduct(product: ProductEntity, newState: Boolean): Completable {
        return if (newState) {
            shamoDao.updateFavouriteProduct(product.id, 1)
        } else {
            shamoDao.updateFavouriteProduct(product.id, 0)
        }
    }

    fun setCartProduct(product: ProductEntity, newState: Int): Completable {
        return shamoDao.updateCartProduct(product.id, newState)
    }

    fun checkFavouriteProduct(id: Int) = shamoDao.checkFavouriteProduct(id)
}