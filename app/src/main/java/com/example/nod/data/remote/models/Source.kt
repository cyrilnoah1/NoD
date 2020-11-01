package com.example.nod.data.remote.models

import com.google.gson.annotations.SerializedName

/**
 * Model to store the source information fetched using the News API.
 */
data class Source(
    @SerializedName(ID) var id: String? = null,
    @SerializedName(NAME) var name: String? = null
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
    }
}