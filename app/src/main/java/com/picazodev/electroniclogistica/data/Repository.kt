package com.picazodev.electroniclogistica.data

import com.picazodev.electroniclogistica.data.remote.DataProperty

interface Repository {

    suspend fun getLocationsList() : List<Location>

    suspend fun getProductsList() : List<Product>

    suspend fun getDataApi() : DataProperty
}