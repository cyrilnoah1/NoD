package com.example.nod.data.local.cache

import androidx.room.Room
import com.example.nod.NewsApplication

/**
 * Singleton to instantiate and provide the local cache handling object.
 */
object CacheProvider {

    private const val NEWS_DATABASE_NAME = "news"

    /**
     * Lazily provides the [NewsDatabase] object that helps in maintaining the local cache.
     */
    val setup: NewsDatabase by lazy {

        Room.databaseBuilder(
            NewsApplication.getAppContext(),
            NewsDatabase::class.java,
            NEWS_DATABASE_NAME
        ).build()
    }
}