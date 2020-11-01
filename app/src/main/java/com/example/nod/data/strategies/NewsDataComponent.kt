package com.example.nod.data.strategies

import dagger.Component

/**
 * Repository Injector.
 *
 * The [NewsDataComponent] helps inject the desired repositories into the classes following
 * the Strategy Pattern to save or retrieve the data.
 */
@Component
interface NewsDataComponent {
    fun inject(strategy: DataBehaviour.NewsInformationBehaviour)
}