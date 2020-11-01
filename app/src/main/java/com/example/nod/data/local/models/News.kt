package com.example.nod.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * News information model that is stored as a row in the [NEWS_TABLE_NAME] table.
 *
 * This model us used for the C.R.U.D operations related to the local cache.
 */
@Entity(tableName = NEWS_TABLE_NAME)
data class News(
    @ColumnInfo(name = AUTHOR) val author: String,
    @ColumnInfo(name = CONTENT_URL) @PrimaryKey val contentUrl: String,
    @ColumnInfo(name = DESCRIPTION) val description: String,
    @ColumnInfo(name = IMAGE_URL) val imageUrl: String,
    @ColumnInfo(name = PUBLISHED_AT) val publishedAt: String,
    @ColumnInfo(name = SOURCE_NAME) val sourceName: String,
    @ColumnInfo(name = TITLE) var title: String
) {

    companion object {
        const val AUTHOR = "author"
        const val CONTENT_URL = "content_url"
        const val DESCRIPTION = "description"
        const val IMAGE_URL = "image_url"
        const val PUBLISHED_AT = "published_at"
        const val SOURCE_NAME = "source_name"
        const val TITLE = "title"
    }
}

const val NEWS_TABLE_NAME = "news"