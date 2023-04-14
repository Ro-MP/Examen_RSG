package com.picazodev.electroniclogistica.domain

import com.picazodev.electroniclogistica.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlgorithmModule {


    @Provides
    @Singleton
    fun provideAlgorithm(repository: Repository): LocationAlgorithm{
        return LocationAlgorithm(repository)
    }

}