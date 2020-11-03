package com.example.nod.data.remote.dataSources

import android.util.Log
import com.example.nod.common.EMPTY_STRING
import com.example.nod.data.remote.services.NewsInformationService
import com.example.nod.data.remote.services.ServiceProvider
import com.example.nod.data.remote.services.SourceResponseBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class SourceRemoteDataSource : SourceDataSource {

    private val service = ServiceProvider.setup.create(NewsInformationService::class.java)
    private var sourceServiceDisposable: CompositeDisposable? = null

    override fun fetchSourceData(
        countryCode: String,
        callback: SourceDataSource.SourceDataResponse
    ) {
        sourceServiceDisposable = CompositeDisposable()

        // Handles the response containing the news data once the
        // result is available.
        val handleResult = Consumer<SourceResponseBody> {
            it.sources?.let { sources -> callback.onSuccess(sources) } ?: run {
                Log.e(TAG, NewsRemoteDataSource.RESPONSE_FAILURE_MESSAGE)
                callback.onFailure(NewsRemoteDataSource.RESPONSE_FAILURE_MESSAGE)
            }
            sourceServiceDisposable?.takeIf { disp -> !disp.isDisposed }?.run { dispose() }
        }

        // Handles any exceptions that had occurred during the service call.
        val handleError = Consumer<Throwable> {
            callback.onFailure(NewsRemoteDataSource.EXCEPTION_FAILURE_MESSAGE)
            Log.e(TAG, it?.message ?: EMPTY_STRING)
            sourceServiceDisposable?.takeIf { disp -> !disp.isDisposed }?.run { dispose() }
        }

        sourceServiceDisposable?.add(
            service.getSources(countryCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResult, handleError)
        )
    }

    companion object {
        val TAG: String = SourceRemoteDataSource::class.java.simpleName
    }
}