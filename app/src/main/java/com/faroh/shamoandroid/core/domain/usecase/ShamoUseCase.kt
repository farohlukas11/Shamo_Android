package com.faroh.shamoandroid.core.domain.usecase

import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.data.source.remote.response.AllProductResponse
import com.faroh.shamoandroid.core.data.source.remote.response.CheckoutProductResponse
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.data.source.remote.response.DataItemTransaction
import com.faroh.shamoandroid.core.data.source.remote.response.LogoutResponse
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.DataCheckout
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import com.faroh.shamoandroid.core.domain.model.UserPreferences
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface ShamoUseCase {
    fun registerUser(registerBody: RegisterBody): Flowable<Resource<RegisterAndLoginResponse>>

    fun loginUser(loginBody: LoginBody): Flowable<Resource<RegisterAndLoginResponse>>

    fun getAllProduct(): Flowable<Resource<List<DataItem>>>

    fun getProductCategories(categoryId: Int): Flowable<Resource<List<DataItem>>>

    fun checkoutProduct(
        token: String,
        dataCheckout: DataCheckout
    ): Flowable<Resource<CheckoutProductResponse>>

    fun getTransaction(token: String): Flowable<Resource<List<DataItemTransaction>>>

    fun logoutUser(token: String): Flowable<Resource<LogoutResponse>>

    suspend fun saveUser(response: RegisterAndLoginResponse)

    fun getUser(): Flow<UserPreferences>

    fun getState(): Flow<Boolean>

    suspend fun isLogout()

    suspend fun isLogin()

    fun getFavouriteProduct(): Flowable<List<DataItem>>

    fun getCartProduct(): Flowable<List<DataItemCart>>

    fun setFavouriteProduct(dataItem: DataItem, newState: Boolean)

    fun setCartProduct(dataItem: DataItemCart)

    fun checkFavouriteProduct(id: Int): Flowable<Int>
}