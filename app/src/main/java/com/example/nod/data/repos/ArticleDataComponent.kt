package com.example.nod.data.repos

import dagger.Component

/**
 * DataSource injector.
 *
 * The [ArticleDataComponent] interface acts as a Dagger [Component] that is used
 * to inject the required data-sources into the repository.
 */
@Component(modules = [ArticleModule::class])
interface ArticleDataComponent {
    fun inject(repo: NewsDataRepository)
}