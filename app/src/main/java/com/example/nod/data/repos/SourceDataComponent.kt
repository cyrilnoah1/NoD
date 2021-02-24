package com.example.nod.data.repos

import dagger.Component

@Component(modules = [SourceModule::class])
interface SourceDataComponent {
    fun inject(repo: SourceDataRepository)
}