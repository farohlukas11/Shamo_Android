package com.faroh.shamoandroid.core.domain.repository

import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.data.source.remote.response.AllProductResponse
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.data.source.remote.response.LogoutResponse
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import com.faroh.shamoandroid.core.domain.model.UserPreferences
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface IShamoRepository {

    //RemoteDataSource
    fun registerUser(registerBody: RegisterBody): Flowable<Resource<RegisterAndLoginResponse>>

    fun loginUser(loginBody: LoginBody): Flowable<Resource<RegisterAndLoginResponse>>

    fun getAllProduct(): Flowable<Resource<List<DataItem>>>

    fun getProductCategories(categoryId: Int): Flowable<Resource<List<DataItem>>>

    //Preferences
    fun logoutUser(token: String): Flowable<Resource<LogoutResponse>>

    suspend fun saveUser(response: RegisterAndLoginResponse)

    fun getUser(): Flow<UserPreferences>

    suspend fun isLogout()

    suspend fun isLogin()

    fun getState(): Flow<Boolean>

    //LocalDataSource

    fun getFavouriteProduct(): Flowable<List<DataItem>>

    fun getCartProduct(): Flowable<List<DataItemCart>>

    fun setFavouriteProduct(dataItem: DataItem, newState: Boolean)

    fun setCartProduct(dataItem: DataItemCart)

    fun checkFavouriteProduct(id: Int): Flowable<Int>
}