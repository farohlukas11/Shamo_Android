package com.faroh.shamoandroid.core.data

import com.faroh.shamoandroid.core.data.source.remote.response.ApiResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressWarnings("UNCHECKED_CAST")
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = PublishSubject.create<Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        val db = dbSource.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                if (shouldFetch(it)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(Resource.Success(it))
                }
            }
        mCompositeDisposable.add(db)
    }

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun saveCallResult(data: RequestType)

    protected abstract fun loadFromDB(): Flowable<ResultType>


    private fun fetchFromNetwork() {

        val apiResponse = createCall()

        result.onNext(Resource.Loading())
        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe {
                when (it) {
                    is ApiResponse.Success -> {
                        saveCallResult(it.data)
                        result.onNext(Resource.Success(it.data) as Resource<ResultType>)
                    }

                    is ApiResponse.Empty -> {
                        result.onNext(Resource.Success(null) as Resource<ResultType>)
                    }

                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(it.errorMessage))
                    }

                    else -> {}
                }
            }
        mCompositeDisposable.add(response)
    }

    fun asFlowable(): Flowable<Resource<ResultType>>? =
        result.toFlowable(BackpressureStrategy.BUFFER)
}