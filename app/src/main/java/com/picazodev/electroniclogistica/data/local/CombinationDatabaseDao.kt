package com.picazodev.electroniclogistica.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.google.android.material.color.utilities.Score
import dagger.Provides

@Dao
interface CombinationDatabaseDao {

    @Insert
    suspend fun insert(combination: Combination)

    @Update
    suspend fun update(combination: Combination)

    @Query("SELECT * FROM electronic_logistica_combination_table WHERE locationKeyMap = :key")
    suspend fun get(key: Long): Combination?

    @Query("DELETE FROM electronic_logistica_combination_table")
    suspend fun clear()

    @Query("SELECT * FROM electronic_logistica_combination_table ORDER BY locationKeyMap ASC")
    fun getAllCombination() : List<Combination>?

}