package com.example.nod.data.remote.models

import com.google.gson.annotations.SerializedName

/**
 * Model to store the article related information that is fetched using the
 * News API.
 */
data class Article(
    @SerializedName(SOURCE) var source: Source? = null,
    @SerializedName(AUTHOR) var author: String? = null,
    @SerializedName(TITLE) var title: String? = null,
    @SerializedName(DESCRIPTION) var description: String? = null,
    @SerializedName(URL) var url: String? = null,
    @SerializedName(URL_TO_IMAGE) var urlToImage: String? = null,
    @SerializedName(PUBLISHED_AT) var publishedAt: String? = null
) {
    companion object {
        const val SOURCE = "source"
        const val AUTHOR = "author"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val URL = "url"
        const val URL_TO_IMAGE = "urlToImage"
        const val PUBLISHED_AT = "publishedAt"
    }
}