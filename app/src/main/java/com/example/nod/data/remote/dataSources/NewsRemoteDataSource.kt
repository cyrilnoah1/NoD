package com.example.nod.data.remote.dataSources

import android.util.Log
import com.example.nod.common.EMPTY_STRING
import com.example.nod.data.remote.services.ArticleResponseBody
import com.example.nod.data.remote.services.NewsInformationService
import com.example.nod.data.remote.services.ServiceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


/**
 * Remote new details fetching data-source.
 *
 * The News fetching DataSource handles the retrieval of latest
 * news content from the internet.
 */
class NewsRemoteDataSource : ArticleDataSource {

    // Retrofit service to fetch for the news information.
    private val service = ServiceProvider.setup.create(NewsInformationService::class.java)
    private var newsServiceDisposable: CompositeDisposable? = null

    /**
     * Fetches the latest news data.
     *
     * Function to fetch for the latest news data from the internet based on the provided
     * [countryCode], using the News API.
     */
    override fun fetchNewsDataByCountryCode(
        countryCode: String,
        callback: ArticleDataSource.ArticleDataResponse
    ) {
        newsServiceDisposable = CompositeDisposable()

        // Handles the response containing the news data once the
        // result is available.
        val handleResult = Consumer<ArticleResponseBody> {
            it.articles?.let { articles -> callback.onSuccess(articles) } ?: run {
                Log.e(TAG, RESPONSE_FAILURE_MESSAGE)
                callback.onFailure(RESPONSE_FAILURE_MESSAGE)
            }
            newsServiceDisposable?.takeIf { disp -> !disp.isDisposed }?.run { dispose() }
        }

        // Handles any exceptions that had occurred during the service call.
        val handleError = Consumer<Throwable> {
            callback.onFailure(EXCEPTION_FAILURE_MESSAGE)
            Log.e(TAG, it?.message)
            newsServiceDisposable?.takeIf { disp -> !disp.isDisposed }?.run { dispose() }
        }

        // Service call to fetch for the remote news data.
        newsServiceDisposable?.add(
            service.getNewsInformationByCountryCode(countryCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResult, handleError)
        )
    }

    override fun fetchNewsDataBySource(
        source: String,
        callback: ArticleDataSource.ArticleDataResponse
    ) {
        newsServiceDisposable = CompositeDisposable()

        // Handles the response containing the news data once the
        // result is available.
        val handleResult = Consumer<ArticleResponseBody> {
            it.articles?.let { articles -> callback.onSuccess(articles) } ?: run {
                Log.e(TAG, RESPONSE_FAILURE_MESSAGE)
                callback.onFailure(RESPONSE_FAILURE_MESSAGE)
            }
            newsServiceDisposable?.takeIf { disp -> !disp.isDisposed }?.run { dispose() }
        }

        // Handles any exceptions that had occurred during the service call.
        val handleError = Consumer<Throwable> {
            callback.onFailure(EXCEPTION_FAILURE_MESSAGE)
            Log.e(TAG, it?.message ?: EMPTY_STRING)
            newsServiceDisposable?.takeIf { disp -> !disp.isDisposed }?.run { dispose() }
        }

        newsServiceDisposable?.add(
            service.getNewsInformationBySource(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResult, handleError)
        )
    }

    companion object {
        val TAG: String = NewsRemoteDataSource::class.java.simpleName
        const val RESPONSE_FAILURE_MESSAGE = "Failed to fetch the new data."
        const val EXCEPTION_FAILURE_MESSAGE = "An exception occurred while retrieving the response."
    }
}