package com.example.nod.data.strategies

import dagger.Component

@Component
interface SourceDataComponent {
    fun inject(strategy: DataBehaviour.SourceInformationBehaviour)
}