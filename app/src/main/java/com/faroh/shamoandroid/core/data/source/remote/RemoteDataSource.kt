package com.faroh.shamoandroid.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.faroh.shamoandroid.core.data.source.remote.network.ApiService
import com.faroh.shamoandroid.core.data.source.remote.response.ApiResponse
import com.faroh.shamoandroid.core.data.source.remote.response.CheckoutProductResponse
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.data.source.remote.response.LogoutResponse
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.DataCheckout
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    @SuppressLint("CheckResult")
    fun registerUser(registerBody: RegisterBody): Flowable<ApiResponse<RegisterAndLoginResponse>> {
        val resultData = PublishSubject.create<ApiResponse<RegisterAndLoginResponse>>()

        apiService.registerUser(registerBody)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe(
                { response ->
                    resultData.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
                }, { error ->
                    resultData.onNext(ApiResponse.Error(error.message.toString()))
                    Log.e("REMOTE REGISTER", error.toString())
                }
            )
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun loginUser(loginBody: LoginBody): Flowable<ApiResponse<RegisterAndLoginResponse>> {
        val resultData = PublishSubject.create<ApiResponse<RegisterAndLoginResponse>>()

        apiService.loginUser(loginBody)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("REMOTE REGISTER", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun logOutUser(token: String): Flowable<ApiResponse<LogoutResponse>> {
        val result = PublishSubject.create<ApiResponse<LogoutResponse>>()

        apiService.logoutUser(token)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("REMOTE REGISTER", error.toString())
            })
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getAllProduct(): Flowable<ApiResponse<List<DataItem>>> {
        val resultData = PublishSubject.create<ApiResponse<List<DataItem>>>()

        apiService.getAllProduct()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val listData = response.data?.data
                resultData.onNext((if (listData?.isNotEmpty() == true) ApiResponse.Success(listData) else ApiResponse.Empty) as ApiResponse<List<DataItem>>)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("REMOTE REGISTER", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getProductCategories(categoryId: Int): Flowable<ApiResponse<List<DataItem>>> {
        val resultData = PublishSubject.create<ApiResponse<List<DataItem>>>()

        apiService.getProductCategories(categoryId)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val listData = response.data?.data
                resultData.onNext((if (listData?.isNotEmpty() == true) ApiResponse.Success(listData) else ApiResponse.Empty) as ApiResponse<List<DataItem>>)

            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("REMOTE REGISTER", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun checkoutProduct(
        dataCheckout: DataCheckout,
        token: String
    ): Flowable<ApiResponse<CheckoutProductResponse>> {
        val resultData = PublishSubject.create<ApiResponse<CheckoutProductResponse>>()

        apiService.checkoutProduct(token, dataCheckout)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("REMOTE REGISTER", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}