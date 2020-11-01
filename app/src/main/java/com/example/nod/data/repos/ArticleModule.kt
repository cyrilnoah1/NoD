package com.example.nod.data.repos

import com.example.nod.data.local.dataSources.NewsCacheDataSource
import com.example.nod.data.remote.dataSources.NewsRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Daggers Article [Module] that [Provides] the required local and remote data-sources.
 */
@Module
class ArticleModule {

    /**
     * Function that [Provides] the [NewsCacheDataSource] instance.
     */
    @Provides
    fun providesNewsCacheDataSource() = NewsCacheDataSource()

    /**
     * Function that [Provides] the [NewsRemoteDataSource] instance.
     */
    @Provides
    fun providesNewsRemoteDataSource() = NewsRemoteDataSource()
}