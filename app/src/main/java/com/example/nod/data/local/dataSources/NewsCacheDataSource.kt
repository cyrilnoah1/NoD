package com.example.nod.data.local.dataSources

import androidx.lifecycle.LiveData
import com.example.nod.data.local.cache.CacheProvider
import com.example.nod.data.local.models.News

/**
 * Local news details caching data-source.
 *
 * The News Caching DataSource handles the local storage of the remotely
 * fetched news in order to serve the purpose of displaying the fetched
 * news details when the user is offline.
 */
class NewsCacheDataSource : NewsDataSource {

    private val cache = CacheProvider.setup.newsDao()

    /**
     * Function to save the [News] information into the local cache.
     */
    override fun saveNewsInformation(news: News) {
        cache.insertNews(news)
    }

    /**
     * Function to save the [News] information list into the local cache.
     */
    override fun saveNewsInformationList(news: List<News>) {
        cache.insertNewsList(news)
    }

    /**
     * Function to retrieve a paginated list of [News] information.
     */
    override fun getNewsInformation(): LiveData<List<News>> {
        return cache.getNewsInformation()
    }

    companion object {
        const val NEWS_PAGE_SIZE = 5
    }
}