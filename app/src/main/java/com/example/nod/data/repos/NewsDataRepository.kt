package com.example.nod.data.repos

import androidx.lifecycle.LiveData
import com.example.nod.data.local.dataSources.NewsCacheDataSource
import com.example.nod.data.local.dataSources.NewsDataSource
import com.example.nod.data.local.models.News
import com.example.nod.data.remote.dataSources.ArticleDataSource
import com.example.nod.data.remote.dataSources.NewsRemoteDataSource
import javax.inject.Inject

class NewsDataRepository : ArticleDataSource, NewsDataSource {

    /**
     * Instantiating the local cache data-source to perform cache related
     * C.R.U.D. and retrieval operations.
     */
    @Inject
    lateinit var cacheData: NewsCacheDataSource

    /**
     * Instantiating the remote data-source to perform remote service related
     * C.R.U.D. and retrieval operations.
     */
    @Inject
    lateinit var remoteData: NewsRemoteDataSource

    init {
        // Injecting the data-source instances using Dependency Injection process.
        DaggerArticleDataComponent.create().inject(this@NewsDataRepository)
    }


    override fun fetchNewsDataByCountryCode(
        countryCode: String,
        callback: ArticleDataSource.ArticleDataResponse
    ) {
        remoteData.fetchNewsDataByCountryCode(countryCode, callback)
    }

    override fun fetchNewsDataBySource(
        source: String,
        callback: ArticleDataSource.ArticleDataResponse
    ) {
        remoteData.fetchNewsDataBySource(source, callback)
    }

    override fun saveNewsInformation(news: News) {
        cacheData.saveNewsInformation(news)
    }

    override fun saveNewsInformationList(news: List<News>) {
        cacheData.saveNewsInformationList(news)
    }

    override fun getNewsInformation(): LiveData<List<News>> {
        return cacheData.getNewsInformation()
    }
}

