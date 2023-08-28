package com.faroh.shamoandroid.core.data.source.remote.network

import com.faroh.shamoandroid.core.data.source.remote.response.AllProductResponse
import com.faroh.shamoandroid.core.data.source.remote.response.CheckoutProductResponse
import com.faroh.shamoandroid.core.data.source.remote.response.LogoutResponse
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.DataCheckout
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type:application/json")
    @POST("register")
    fun registerUser(
        @Body registerBody: RegisterBody
    ): Flowable<RegisterAndLoginResponse>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUser(
        @Body loginBody: LoginBody
    ): Flowable<RegisterAndLoginResponse>

    @Headers("Content-Type:application/json")
    @POST("logout")
    fun logoutUser(
        @Header("Authorization") token: String
    ): Flowable<LogoutResponse>

    @Headers("Content-Type:application/json")
    @GET("products?limit=20")
    fun getAllProduct(): Flowable<AllProductResponse>

    @Headers("Content-Type:application/json")
    @GET("products?limit=20")
    fun getProductCategories(
        @Query("categories") categoryId: Int
    ): Flowable<AllProductResponse>

    @Headers("Content-Type:application/json")
    @POST("checkout")
    fun checkoutProduct(
        @Header("Authorization") token: String,
        @Body dataCheckout: DataCheckout
    ): Flowable<CheckoutProductResponse>

}