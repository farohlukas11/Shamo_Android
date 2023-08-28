package com.faroh.shamoandroid.core.utils

import com.faroh.shamoandroid.core.data.source.local.entity.ProductAndGallery
import com.faroh.shamoandroid.core.data.source.local.entity.ProductEntity
import com.faroh.shamoandroid.core.data.source.local.entity.ProductGalleryEntity
import com.faroh.shamoandroid.core.data.source.remote.response.Category
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.data.source.remote.response.GalleriesItem
import com.faroh.shamoandroid.core.data.source.remote.response.ItemsCheckout
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.model.ItemCheckOut

object DataMapper {

    //get from database
    fun mapProductEntitiesToDomain(input: List<ProductAndGallery>): List<DataItem> = input.map {
        DataItem(
            id = it.product.id,
            name = it.product.name,
            categoriesId = it.product.categoryid,
            category = Category(name = it.product.categoryName, id = it.product.categoryid),
            price = it.product.price,
            description = it.product.description,
            galleries = mapGalleryEntitiesToDomain(it.galleries)
        )
    }

    private fun mapGalleryEntitiesToDomain(input: List<ProductGalleryEntity>): List<GalleriesItem> =
        input.map {
            GalleriesItem(
                url = it.imageUrl
            )
        }

    fun mapProductEntitiesToDomainCart(input: List<ProductAndGallery>): List<DataItemCart> =
        input.map {
            DataItemCart(
                dataItem = DataItem(
                    id = it.product.id,
                    name = it.product.name,
                    categoriesId = it.product.categoryid,
                    category = Category(
                        name = it.product.categoryName,
                        id = it.product.categoryid
                    ),
                    price = it.product.price,
                    description = it.product.description,
                    galleries = mapGalleryEntitiesToDomain(it.galleries)
                ),
                inCart = it.product.inCart
            )
        }


    //insert to database
    fun mapListProductDomainToEntity(input: List<DataItem?>): List<ProductEntity> {
        val dataProductList = ArrayList<ProductEntity>()
        input.map {
            val product = ProductEntity(
                id = it?.id!!,
                description = it.description!!,
                categoryName = it.category?.name!!,
                price = it.price!!,
                name = it.name!!,
                categoryid = it.categoriesId!!
            )
            dataProductList.add(product)
        }
        return dataProductList
    }

    fun mapListGalleryDomainToEntity(
        input: List<GalleriesItem?>,
        prodId: Int?
    ): List<ProductGalleryEntity> {
        val listGalleries = ArrayList<ProductGalleryEntity>()
        input.map {
            val gallery = ProductGalleryEntity(
                imageUrl = it?.url!!,
                id = it.id!!,
                prodId = prodId!!
            )
            listGalleries.add(gallery)
        }
        return listGalleries
    }

    fun mapProductDomainToEntity(input: DataItem) = ProductEntity(
        id = input.id!!,
        name = input.name!!,
        categoryid = input.categoriesId!!,
        categoryName = input.category?.name!!,
        price = input.price!!,
        description = input.description!!
    )

    fun mapDataItemToDataCheckout(input: List<DataItemCart>): List<ItemCheckOut> = input.map {
        ItemCheckOut(
            id = it.dataItem.id,
            quantity = it.inCart
        )
    }
}
