package com.faroh.shamoandroid.core.domain.usecase

import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.data.source.remote.response.LogoutResponse
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import com.faroh.shamoandroid.core.domain.model.UserPreferences
import com.faroh.shamoandroid.core.domain.repository.IShamoRepository
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShamoInteractor @Inject constructor(private val shamoRepository: IShamoRepository) :
    ShamoUseCase {
    override fun registerUser(registerBody: RegisterBody): Flowable<Resource<RegisterAndLoginResponse>> {
        return shamoRepository.registerUser(registerBody)
    }

    override fun loginUser(loginBody: LoginBody): Flowable<Resource<RegisterAndLoginResponse>> {
        return shamoRepository.loginUser(loginBody)
    }

    override fun logoutUser(token: String): Flowable<Resource<LogoutResponse>> {
        return shamoRepository.logoutUser(token)
    }

    override fun getAllProduct(): Flowable<Resource<List<DataItem>>> {
        return shamoRepository.getAllProduct()
    }

    override fun getProductCategories(categoryId: Int): Flowable<Resource<List<DataItem>>> {
        return shamoRepository.getProductCategories(categoryId)
    }

    override suspend fun saveUser(response: RegisterAndLoginResponse) {
        shamoRepository.saveUser(response)
    }

    override fun getUser(): Flow<UserPreferences> = shamoRepository.getUser()

    override fun getState(): Flow<Boolean> = shamoRepository.getState()

    override suspend fun isLogin() {
        shamoRepository.isLogin()
    }

    override suspend fun isLogout() {
        shamoRepository.isLogout()
    }

    override fun getFavouriteProduct(): Flowable<List<DataItem>> {
        return shamoRepository.getFavouriteProduct()
    }

    override fun getCartProduct(): Flowable<List<DataItemCart>> {
        return shamoRepository.getCartProduct()
    }

    override fun setFavouriteProduct(dataItem: DataItem, newState: Boolean) {
        shamoRepository.setFavouriteProduct(dataItem, newState)
    }

    override fun setCartProduct(dataItem: DataItemCart) {
        shamoRepository.setCartProduct(dataItem)
    }

    override fun checkFavouriteProduct(id: Int): Flowable<Int> {
        return shamoRepository.checkFavouriteProduct(id)
    }
}