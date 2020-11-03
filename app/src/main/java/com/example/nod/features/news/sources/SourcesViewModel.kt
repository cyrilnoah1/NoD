package com.example.nod.features.news.sources

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nod.R
import com.example.nod.common.BaseAndroidViewModel
import com.example.nod.common.EMPTY_STRING
import com.example.nod.data.strategies.DataBehaviour
import com.example.nod.data.strategies.news.SourceInformationStrategy

class SourcesViewModel(application: Application) : BaseAndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val appContext = application.applicationContext
    private val sourceStrategy: DataBehaviour.SourceInformationBehaviour =
        SourceInformationStrategy()

    val obsError = MutableLiveData<String>()
    val obsProgress = MutableLiveData<Boolean>()
    val obsSources = sourceStrategy.getSourceInformation()


    override fun onCleared() {
        super.onCleared()
        // Cancelling any available pending processes.
        sourceStrategy.cancelPendingProcesses()
    }

    fun fetchSources() {
        sourceStrategy.fetchAndSaveSourceInformation(
            US_COUNTRY_CODE,
            object : DataBehaviour.SourceInformationBehaviour.ResultCallback {
                override fun onSuccess() {
                    obsProgress.postValue(false)
                    Log.d(TAG, "Successfully retrieved the sources information.")
                    obsError.value = EMPTY_STRING
                }

                override fun onFailure() {
                    obsProgress.postValue(false)
                    obsError.value =
                        appContext.getString(R.string.source_info_retrieval_failure_msg)
                }

            }
        )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SourcesViewModel(application) as T
        }
    }

    companion object {
        val TAG: String = SourcesViewModel::class.java.simpleName
        const val INDIA_COUNTRY_CODE = "in"
        const val US_COUNTRY_CODE = "us"
    }
}