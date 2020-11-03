package com.example.nod.data.strategies

import androidx.lifecycle.LiveData
import com.example.nod.data.local.models.News
import com.example.nod.data.local.models.Source
import com.example.nod.features.news.newsList.QueryType

/**
 * Strategy Pattern to help retrieve the news information from a desired location.
 */
interface DataBehaviour {

    /**
     * Behaviour that handles the strategy related to the local caching operation.
     */
    interface NewsInformationBehaviour {
        fun fetchAndSaveNewsInformation(
            query: String,
            queryType: QueryType,
            callback: ResultCallback
        )

        fun getNewsInformation(): LiveData<List<News>>
        fun cancelPendingProcesses()

        interface ResultCallback {
            fun onSuccess()
            fun onFailure()
        }
    }

    interface SourceInformationBehaviour {
        fun fetchAndSaveSourceInformation(countryCode: String, callback: ResultCallback)
        fun getSourceInformation(): LiveData<List<Source>>
        fun cancelPendingProcesses()

        interface ResultCallback {
            fun onSuccess()
            fun onFailure()
        }
    }
}