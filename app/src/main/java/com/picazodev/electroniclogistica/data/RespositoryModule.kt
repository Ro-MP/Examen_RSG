package com.picazodev.electroniclogistica.data

import com.picazodev.electroniclogistica.data.local.CombinationDatabase
import com.picazodev.electroniclogistica.data.local.CombinationDatabaseDao
import com.picazodev.electroniclogistica.data.remote.DataApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton
import javax.sql.DataSource

@Module
@InstallIn(SingletonComponent::class)
object RespositoryModule {


    @Provides
    @Singleton
    fun provideRepository(dataApi: DataApi, dataSource: CombinationDatabase): Repository {
        Timber.i("DataApi: $dataApi")
        Timber.i("DataSource:  $dataSource")
        return RepositoryImpl(dataApi, dataSource)
    }
}