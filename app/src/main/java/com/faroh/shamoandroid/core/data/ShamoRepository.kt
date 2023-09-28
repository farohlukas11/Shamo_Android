package com.faroh.shamoandroid.core.data

import com.faroh.shamoandroid.core.data.source.local.LocalDataSource
import com.faroh.shamoandroid.core.data.source.preferences.ShamoPreferences
import com.faroh.shamoandroid.core.data.source.remote.RemoteDataSource
import com.faroh.shamoandroid.core.data.source.remote.response.ApiResponse
import com.faroh.shamoandroid.core.data.source.remote.response.CheckoutProductResponse
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.data.source.remote.response.LogoutResponse
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.domain.model.DataCheckout
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import com.faroh.shamoandroid.core.domain.model.UserPreferences
import com.faroh.shamoandroid.core.domain.repository.IShamoRepository
import com.faroh.shamoandroid.core.utils.DataMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShamoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val shamoPreferences: ShamoPreferences,
    private val localDataSource: LocalDataSource
) : IShamoRepository {

    //RemoteDataSource
    override fun registerUser(registerBody: RegisterBody): Flowable<Resource<RegisterAndLoginResponse>> {
        val result = PublishSubject.create<Resource<RegisterAndLoginResponse>>()
        val mCompositeDisposable = CompositeDisposable()

        result.onNext(Resource.Loading())
        val registerUser = remoteDataSource.registerUser(registerBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe {
                when (it) {
                    is ApiResponse.Success -> {
                        result.onNext(Resource.Success(it.data))
                    }

                    is ApiResponse.Empty -> {
                        result.onNext(Resource.Success(RegisterAndLoginResponse()))
                    }

                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(it.errorMessage))
                    }

                    else -> {}
                }
            }
        mCompositeDisposable.add(registerUser)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun loginUser(loginBody: LoginBody): Flowable<Resource<RegisterAndLoginResponse>> {
        val result = PublishSubject.create<Resource<RegisterAndLoginResponse>>()
        val mCompositeDisposable = CompositeDisposable()

        result.onNext(Resource.Loading())
        val loginUser = remoteDataSource.loginUser(loginBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe {
                when (it) {
                    is ApiResponse.Success -> {
                        result.onNext(Resource.Success(it.data))
                    }

                    is ApiResponse.Empty -> {
                        result.onNext(Resource.Success(RegisterAndLoginResponse()))
                    }

                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(it.errorMessage))
                    }

                    else -> {}
                }
            }
        mCompositeDisposable.add(loginUser)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun logoutUser(token: String): Flowable<Resource<LogoutResponse>> {
        val result = PublishSubject.create<Resource<LogoutResponse>>()
        val mCompositeDisposable = CompositeDisposable()

        val logoutUser = remoteDataSource.logOutUser("Bearer $token")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe {
                when (it) {
                    is ApiResponse.Success -> {
                        result.onNext(Resource.Success(it.data))
                    }

                    is ApiResponse.Empty -> {
                        result.onNext(Resource.Success(LogoutResponse()))
                    }

                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(it.errorMessage))
                    }

                    else -> {}
                }
            }
        mCompositeDisposable.add(logoutUser)
        return result.toFlowable(BackpressureStrategy.BUFFER)

    }

    override fun getAllProduct(): Flowable<Resource<List<DataItem>>> =
        object : NetworkBoundResource<List<DataItem>, List<DataItem>>() {

            override fun createCall(): Flowable<ApiResponse<List<DataItem>>> {
                return remoteDataSource.getAllProduct()
            }

            override fun loadFromDB(): Flowable<List<DataItem>> {
                return localDataSource.getAllProductAndGalleries().map {
                    DataMapper.mapProductEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<DataItem>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun saveCallResult(data: List<DataItem>) {
                if (data.isNotEmpty()) {
                    localDataSource.insertProduct(DataMapper.mapListProductDomainToEntity(data))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

                    for (product in data) {
                        localDataSource.insertGalleries(
                            DataMapper.mapListGalleryDomainToEntity(
                                product.galleries!!,
                                product.id
                            )
                        ).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    }
                }

            }
        }.asFlowable() as Flowable<Resource<List<DataItem>>>

    override fun getProductCategories(categoryId: Int): Flowable<Resource<List<DataItem>>> =
        object : NetworkBoundResource<List<DataItem>, List<DataItem>>() {
            override fun createCall(): Flowable<ApiResponse<List<DataItem>>> {
                return remoteDataSource.getProductCategories(categoryId)
            }

            override fun loadFromDB(): Flowable<List<DataItem>> {
                return localDataSource.getProductCategories(categoryId).map {
                    DataMapper.mapProductEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<DataItem>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun saveCallResult(data: List<DataItem>) {
                if (data.isNotEmpty()) {
                    localDataSource.insertProduct(DataMapper.mapListProductDomainToEntity(data))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

                    for (product in data) {
                        localDataSource.insertGalleries(
                            DataMapper.mapListGalleryDomainToEntity(
                                product.galleries!!,
                                product.id
                            )
                        ).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    }
                }
            }
        }.asFlowable() as Flowable<Resource<List<DataItem>>>

    override fun checkoutProduct(
        token: String,
        dataCheckout: DataCheckout
    ): Flowable<Resource<CheckoutProductResponse>> {
        val result = PublishSubject.create<Resource<CheckoutProductResponse>>()
        val mCompositeDisposable = CompositeDisposable()

        result.onNext(Resource.Loading())
        val checkoutProduct = remoteDataSource.checkoutProduct(dataCheckout, "Bearer $token")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe {
                when (it) {
                    is ApiResponse.Success -> {
                        result.onNext(Resource.Success(it.data))
                    }

                    is ApiResponse.Empty -> {
                        result.onNext(Resource.Success(CheckoutProductResponse()))
                    }

                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(it.errorMessage))
                    }

                    else -> {}
                }
            }
        mCompositeDisposable.add(checkoutProduct)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    //Preferences
    override suspend fun saveUser(response: RegisterAndLoginResponse) {
        shamoPreferences.saveUser(response)
    }

    override fun getUser(): Flow<UserPreferences> = shamoPreferences.getUser()

    override suspend fun isLogin() {
        shamoPreferences.setState(true)
    }

    override suspend fun isLogout() {
        shamoPreferences.setState(false)
    }

    override fun getState(): Flow<Boolean> = shamoPreferences.getState()

    //db
    override fun getFavouriteProduct(): Flowable<List<DataItem>> =
        localDataSource.getFavouriteProduct().map {
            DataMapper.mapProductEntitiesToDomain(it)
        }

    override fun getCartProduct(): Flowable<List<DataItemCart>> {
        val result = PublishSubject.create<List<DataItemCart>>()
        val mCompositeDisposable = CompositeDisposable()

        val cartProduct = localDataSource.getCartProduct()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe {
                result.onNext(DataMapper.mapProductEntitiesToDomainCart(it))
            }
        mCompositeDisposable.add(cartProduct)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }


    override fun setFavouriteProduct(dataItem: DataItem, newState: Boolean) {
        localDataSource.setFavouriteProduct(DataMapper.mapProductDomainToEntity(dataItem), newState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun setCartProduct(dataItem: DataItemCart) {
        localDataSource.setCartProduct(
            DataMapper.mapProductDomainToEntity(dataItem.dataItem),
            dataItem.inCart
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun checkFavouriteProduct(id: Int): Flowable<Int> =
        localDataSource.checkFavouriteProduct(id)
}