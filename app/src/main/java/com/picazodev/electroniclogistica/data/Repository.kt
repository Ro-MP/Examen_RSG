package com.picazodev.electroniclogistica.data

import com.picazodev.electroniclogistica.data.local.Combination
import com.picazodev.electroniclogistica.data.remote.DataProperty

interface Repository {

    suspend fun getLocationsList() : List<Location>

    suspend fun getProductsList() : List<Product>

    suspend fun getDataApi() : DataProperty


    suspend fun getLocationsMap(): Map<String, Location>

    suspend fun getProductsMap(): Map<String, Product>

    suspend fun addCombination(sortedProductIndexList: List<Int>)

    suspend fun getCombinatiosList(): List<Combination>?
}