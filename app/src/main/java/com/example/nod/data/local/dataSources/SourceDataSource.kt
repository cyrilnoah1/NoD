package com.example.nod.data.local.dataSources

import androidx.lifecycle.LiveData
import com.example.nod.data.local.models.Source

interface SourceDataSource {

    fun saveSource(source: Source)

    fun saveSourcesList(sources: List<Source>)

    fun getSources(): LiveData<List<Source>>
}