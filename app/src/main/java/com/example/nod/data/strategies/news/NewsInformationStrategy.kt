package com.example.nod.data.strategies.news

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.nod.data.local.models.News
import com.example.nod.data.remote.dataSources.ArticleDataSource
import com.example.nod.data.remote.models.Article
import com.example.nod.data.repos.NewsDataRepository
import com.example.nod.data.strategies.DaggerNewsDataComponent
import com.example.nod.data.strategies.DataBehaviour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import toNews

/**
 * Strategy Pattern class that provides required news information by using the
 * respective repositories for performing the operations.
 */
class NewsInformationStrategy : DataBehaviour.NewsInformationBehaviour {

    /**
     * Instantiating the repository object dynamically using Dependency Injection process.
     *
     * The repository will be used to supply the news information to the UI related classes.
     */
    private var newsRepo = NewsDataRepository()

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(job + Dispatchers.IO)

    init {
        // Performing injection of repository object.
        DaggerNewsDataComponent.create().inject(this@NewsInformationStrategy)
    }

    override fun fetchAndSaveNewsInformation(
        countryCode: String,
        callback: DataBehaviour.NewsInformationBehaviour.ResultCallback
    ) {

        newsRepo.fetchNewsData(
            countryCode,
            object : ArticleDataSource.ArticleDataResponse {

                override fun onSuccess(articles: List<Article>) {
                    // Once the article information has been successfully received, converting
                    // and storing the information in the local cache.
                    ioScope.launch { newsRepo.saveNewsInformationList(articles.map { it.toNews() }) }
                    callback.onSuccess()
                }

                override fun onFailure(message: String) {
                    callback.onFailure()
                    Log.e(TAG, message)
                }
            }
        )
    }

    override fun getNewsInformation(): LiveData<List<News>> {
        return newsRepo.getNewsInformation()
    }

    override fun cancelPendingProcesses() {
        // Cancelling all the pending child processes, provided any are left.
        job.cancel()
    }

    companion object {
        val TAG: String = NewsInformationStrategy::class.java.simpleName
    }
}