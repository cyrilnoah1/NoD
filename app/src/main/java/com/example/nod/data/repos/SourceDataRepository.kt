package com.example.nod.data.repos

import androidx.lifecycle.LiveData
import com.example.nod.data.local.dataSources.SourceCacheDataSource
import com.example.nod.data.local.models.Source
import com.example.nod.data.remote.dataSources.SourceDataSource
import com.example.nod.data.remote.dataSources.SourceRemoteDataSource
import javax.inject.Inject
import com.example.nod.data.local.dataSources.SourceDataSource as CacheSourceDataSource

class SourceDataRepository : SourceDataSource, CacheSourceDataSource {

    /**
     * Instantiating the local cache data-source to perform cache related
     * C.R.U.D. and retrieval operations.
     */
    @Inject
    lateinit var cacheData: SourceCacheDataSource

    /**
     * Instantiating the remote data-source to perform remote service related
     * C.R.U.D. and retrieval operations.
     */
    @Inject
    lateinit var remoteData: SourceRemoteDataSource

    init {
        // Injecting the data-source instances using Dependency Injection process.
        DaggerSourceDataComponent.create().inject(this@SourceDataRepository)
    }

    override fun fetchSourceData(
        countryCode: String,
        callback: SourceDataSource.SourceDataResponse
    ) {
        remoteData.fetchSourceData(countryCode, callback)
    }

    override fun saveSource(source: Source) {
        cacheData.saveSource(source)
    }

    override fun saveSourcesList(sources: List<Source>) {
        cacheData.saveSourcesList(sources)
    }

    override fun getSources(): LiveData<List<Source>> {
        return cacheData.getSources()
    }
}

