package com.example.nod.data.remote.dataSources

import com.example.nod.data.remote.models.Source

/**
 * DataSource to handle the operations required to retrieve remote source information data.
 */
interface SourceDataSource {

    /**
     * Function to fetch for the latest source data from the internet based on the provided
     * [countryCode], using the News API.
     */
    fun fetchSourceData(countryCode: String, callback: SourceDataResponse)

    /**
     * Callback to provide the response of the new service call.
     */
    interface SourceDataResponse {
        fun onSuccess(sources: List<Source>)
        fun onFailure(message: String)
    }
}