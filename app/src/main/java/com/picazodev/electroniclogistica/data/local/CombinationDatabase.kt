package com.picazodev.electroniclogistica.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Combination::class], version = 1, exportSchema = false)
abstract class CombinationDatabase : RoomDatabase() {

    abstract fun combinationDatabaseDao(): CombinationDatabaseDao


}