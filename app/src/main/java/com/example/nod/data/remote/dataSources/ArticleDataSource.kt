package com.example.nod.data.remote.dataSources

import com.example.nod.data.remote.models.Article

/**
 * DataSource to handle the operations required to retrieve remote news information data.
 */
interface ArticleDataSource {

    /**
     * Function to fetch for the latest news data from the internet based on the provided
     * [countryCode], using the News API.
     */
    fun fetchNewsData(countryCode: String, callback: ArticleDataResponse)

    /**
     * Callback to provide the response of the new service call.
     */
    interface ArticleDataResponse {
        fun onSuccess(articles: List<Article>)
        fun onFailure(message: String)
    }
}