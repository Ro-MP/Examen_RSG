package com.picazodev.electroniclogistica.data.local


import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {



    @Provides
    @Singleton
    fun provideDataBaseInstance( app: Application) : CombinationDatabase {
        return Room.databaseBuilder(
            app,
            CombinationDatabase::class.java,
            "electronic_logistica_combination_table"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}