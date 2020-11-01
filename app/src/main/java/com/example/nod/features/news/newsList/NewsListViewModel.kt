package com.example.nod.features.news.newsList

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nod.R
import com.example.nod.common.BaseAndroidViewModel
import com.example.nod.common.EMPTY_STRING
import com.example.nod.data.local.models.News
import com.example.nod.data.strategies.DataBehaviour
import com.example.nod.data.strategies.news.NewsInformationStrategy

/**
 * ViewModel that helps in fetching for the [News] information list from the internet and stores
 * the retrieved data in the local cache.
 *
 * Once the local cache is updated, the ViewModel notifies the respective Fragments/Activities
 * that are subscribed to listen to the local cache changes.
 */
class NewsListViewModel(application: Application) : BaseAndroidViewModel(application) {

    // Ignoring the warning as it is the application context.
    @SuppressLint("StaticFieldLeak")
    private val appContext = application.applicationContext
    private val newsStrategy: DataBehaviour.NewsInformationBehaviour = NewsInformationStrategy()

    val obsError = MutableLiveData<String>()
    val obsProgress = MutableLiveData<Boolean>()
    val obsNewsList = newsStrategy.getNewsInformation()


    override fun onCleared() {
        super.onCleared()
        // Cancelling any available pending processes.
        newsStrategy.cancelPendingProcesses()
    }

    fun fetchNewsInformation() {
        obsProgress.postValue(true)
        newsStrategy.fetchAndSaveNewsInformation(
            INDIA_COUNTRY_CODE,
            object : DataBehaviour.NewsInformationBehaviour.ResultCallback {

                override fun onSuccess() {
                    obsProgress.postValue(false)
                    Log.d(TAG, "Successfully retrieved the news information.")
                    obsError.value = EMPTY_STRING
                }

                override fun onFailure() {
                    obsProgress.postValue(false)
                    obsError.value = appContext.getString(R.string.news_info_retrieval_failure_msg)
                }
            })
    }

    // Ignoring the warning as it is the application context.
    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsListViewModel(application) as T
        }
    }

    companion object {
        val TAG: String = NewsListViewModel::class.java.simpleName
        const val INDIA_COUNTRY_CODE = "in"
        const val US_COUNTRY_CODE = "us"
        const val CANADA_COUNTRY_CODE = "ca"
        const val AUSTRALIA_COUNTRY_CODE = "au"
    }
}