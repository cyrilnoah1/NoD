package com.example.nod.data.remote.services

import com.example.nod.data.remote.API_KEY
import com.example.nod.data.remote.API_VERSION_V2
import com.example.nod.data.remote.COUNTRY_CODE_KEY
import com.example.nod.data.remote.NEWS_API_KEY
import com.example.nod.data.remote.models.Article
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
    fun getNewsInformation(
        @Query(COUNTRY_CODE_KEY) countryCode: String,
        @Query(NEWS_API_KEY) apiKey: String = API_KEY
    ): Observable<ResponseBody>
}

/**
 * Model to hold the response information provided by the News API.
 */
data class ResponseBody(
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



