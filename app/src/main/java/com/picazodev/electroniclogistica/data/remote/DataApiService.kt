package com.picazodev.electroniclogistica.data.remote

import com.google.gson.Gson
import java.io.FileReader
import java.io.InputStream


interface DataApiService {


    suspend fun getDataProperty(): DataProperty


}


class DataApi private constructor(private val jsonData: InputStream) : DataApiService {

    private val gson = Gson()
    private var dataProperty: DataProperty =
        gson.fromJson(jsonData.bufferedReader().use { it.readText() }, DataProperty::class.java)



    companion object {

        private var instance: DataApi? = null

        fun getInstance(jsonData: InputStream): DataApi {
            if (instance == null){
                instance = DataApi(jsonData)
            }
            return instance as DataApi
        }


    }


    override suspend fun getDataProperty(): DataProperty {
        return dataProperty
    }


}


