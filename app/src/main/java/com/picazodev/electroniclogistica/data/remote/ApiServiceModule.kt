package com.picazodev.electroniclogistica.data.remote

import android.app.Application
import android.content.Context
import com.picazodev.electroniclogistica.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideDataApi( app: Application): DataApi{
        val jsonResources = app.resources.openRawResource(R.raw.data)

        return DataApi(jsonResources)

    }


}