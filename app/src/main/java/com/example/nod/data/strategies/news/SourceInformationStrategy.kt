package com.example.nod.data.strategies.news

import androidx.lifecycle.LiveData
import com.example.nod.common.EMPTY_STRING
import com.example.nod.data.remote.dataSources.SourceDataSource
import com.example.nod.data.remote.models.Source
import com.example.nod.data.repos.SourceDataRepository
import com.example.nod.data.strategies.DaggerSourceDataComponent
import com.example.nod.data.strategies.DataBehaviour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.example.nod.data.local.models.Source as CacheSource

class SourceInformationStrategy : DataBehaviour.SourceInformationBehaviour {

    private var sourceRepo = SourceDataRepository()

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(job + Dispatchers.IO)

    init {
        DaggerSourceDataComponent.create().inject(this@SourceInformationStrategy)
    }

    override fun fetchAndSaveSourceInformation(
        countryCode: String,
        callback: DataBehaviour.SourceInformationBehaviour.ResultCallback
    ) {
        sourceRepo.fetchSourceData(countryCode, object : SourceDataSource.SourceDataResponse {
            override fun onSuccess(sources: List<Source>) {
                ioScope.launch {
                    sourceRepo.saveSourcesList(sources.map {
                        CacheSource(
                            it.id ?: EMPTY_STRING,
                            it.name ?: EMPTY_STRING,
                            it.description ?: EMPTY_STRING
                        )
                    })
                }
                callback.onSuccess()
            }

            override fun onFailure(message: String) {
                callback.onFailure()
            }

        })
    }

    override fun getSourceInformation(): LiveData<List<CacheSource>> {
        return return sourceRepo.getSources()
    }

    override fun cancelPendingProcesses() {
        job.cancel()
    }
}