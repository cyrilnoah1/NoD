package com.example.nod.data.local.dataSources

import androidx.lifecycle.LiveData
import com.example.nod.data.local.cache.CacheProvider
import com.example.nod.data.local.models.Source

class SourceCacheDataSource : SourceDataSource {

    private val cache = CacheProvider.setup.sourceDao()

    override fun saveSource(source: Source) {
        cache.insertSource(source)
    }

    override fun saveSourcesList(sources: List<Source>) {
        cache.insertSources(sources)
    }

    override fun getSources(): LiveData<List<Source>> {
        return cache.getSourcesInformation()
    }

}