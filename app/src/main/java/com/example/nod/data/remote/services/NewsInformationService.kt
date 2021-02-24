package com.example.nod.data.remote.services

import com.example.nod.data.remote.*
import com.example.nod.data.remote.models.Article
import com.example.nod.data.remote.models.Source
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Contains remote service calls related to the news information.
 */
interface NewsInformationService {

    /**
     * Service call to fetch the remote News data based on the provided country code.
     */
    @GET("${API_VERSION_V2}/top-headlines")
    fun getNewsInformationByCountryCode(
        @Query(COUNTRY_CODE_KEY) countryCode: String,
        @Query(NEWS_API_KEY) apiKey: String = API_KEY
    ): Observable<ArticleResponseBody>

    @GET("${API_VERSION_V2}/everything")
    fun getNewsInformationBySource(
        @Query(SOURCES_KEY) source: String,
        @Query(NEWS_API_KEY) apiKey: String = API_KEY
    ): Observable<ArticleResponseBody>


    @GET("${API_VERSION_V2}/sources")
    fun getSources(
        @Query(COUNTRY_CODE_KEY) countryCode: String,
        @Query(NEWS_API_KEY) apiKey: String = API_KEY
    ): Observable<SourceResponseBody>
}

/**
 * Model to hold the response information provided by the News API.
 */
data class ArticleResponseBody(
    @SerializedName(STATUS) var status: String? = null,
    @SerializedName(TOTAL_RESULTS) var totalResults: Int? = null,
    @SerializedName(ARTICLES) var articles: List<Article>? = null
) {
    companion object {
        const val STATUS = "status"
        const val TOTAL_RESULTS = "totalResults"
        const val ARTICLES = "articles"
    }
}

data class SourceResponseBody(
    @SerializedName(STATUS) var status: String? = null,
    @SerializedName(SOURCES) var sources: List<Source>? = null
) {
    companion object {
        const val STATUS = "status"
        const val SOURCES = "sources"
    }
}



