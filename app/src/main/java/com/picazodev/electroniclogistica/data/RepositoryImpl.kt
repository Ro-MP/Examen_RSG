package com.picazodev.electroniclogistica.data

import com.picazodev.electroniclogistica.data.local.Combination
import com.picazodev.electroniclogistica.data.local.CombinationDatabaseDao
import com.picazodev.electroniclogistica.data.remote.DataApi
import com.picazodev.electroniclogistica.data.remote.DataProperty
import com.picazodev.electroniclogistica.toKeyForLocationMap
import com.picazodev.electroniclogistica.toKeyForProductMap

class RepositoryImpl(val dataApi: DataApi, val dataSource: CombinationDatabaseDao) : Repository {


    /*
        Genera Maps de las listas de Product y Location
        con sus indices como parte de la Key
     */
    suspend fun getLocationsMap(): Map<String, Location>{
        val locationsList = getLocationsList()
        val locationsMap = mutableMapOf<String, Location>()
        for (index in locationsList.indices){
            val key = index.toKeyForLocationMap()
            locationsMap[key] = locationsList[index]
        }
        return locationsMap
    }

    suspend fun getProductsMap(): Map<String, Product>{
        val productsList = getProductsList()
        val productMap = mutableMapOf<String, Product>()
        for (index in productsList.indices){
            val key = index.toKeyForProductMap()
            productMap[key] = productsList[index]
        }
        return productMap
    }



    override suspend fun getLocationsList(): List<Location> {
        return getDataApi().locations
    }

    override suspend fun getProductsList(): List<Product> {
        return getDataApi().products
    }

    override suspend fun getDataApi(): DataProperty {
        println("# ## # ${dataApi}")
        return dataApi.getDataProperty()
    }


    suspend fun addCombination(sortedProductIndexList: List<Int>){
        val locationList = getLocationsList()

        for (index in locationList.indices){
            val combination = Combination(
                index.toKeyForLocationMap(), sortedProductIndexList[index].toKeyForProductMap())

            dataSource.insert(combination)
        }
    }

    suspend fun getCombinatiosList(): List<Combination>? {
        return dataSource.getAllCombination()
    }

}

