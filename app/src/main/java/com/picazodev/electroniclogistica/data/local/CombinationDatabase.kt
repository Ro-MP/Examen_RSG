package com.picazodev.electroniclogistica.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Combination::class], version = 1, exportSchema = false)
abstract class CombinationDatabase : RoomDatabase() {

    abstract val combinationDatabaseDao: CombinationDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: CombinationDatabase? = null

        fun getInstance(context: Context): CombinationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CombinationDatabase::class.java,
                        "electronic_logistica_combination_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}