package com.example.nod.data.repos

import com.example.nod.data.local.dataSources.SourceCacheDataSource
import com.example.nod.data.remote.dataSources.SourceRemoteDataSource
import dagger.Module
import dagger.Provides


@Module
class SourceModule {

    @Provides
    fun providesSourcesCacheDataSource() = SourceCacheDataSource()

    @Provides
    fun providesSourcesRemoteDataSource() = SourceRemoteDataSource()
}