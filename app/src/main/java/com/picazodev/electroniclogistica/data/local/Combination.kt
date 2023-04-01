package com.picazodev.electroniclogistica.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "electronic_logistica_combination_table")
data class Combination(
    @PrimaryKey
    var locationKeyMap: String = "",

    @ColumnInfo
    var productKeyMap: String = ""

)
