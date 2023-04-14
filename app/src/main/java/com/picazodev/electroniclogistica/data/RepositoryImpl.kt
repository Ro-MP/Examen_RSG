package com.picazodev.electroniclogistica.data

import android.provider.SyncStateContract.Helpers.insert
import com.picazodev.electroniclogistica.data.local.Combination
import com.picazodev.electroniclogistica.data.local.CombinationDatabase
import com.picazodev.electroniclogistica.data.local.CombinationDatabaseDao
import com.picazodev.electroniclogistica.data.remote.DataApi
import com.picazodev.electroniclogistica.data.remote.DataProperty
import com.picazodev.electroniclogistica.util.toKeyForLocationMap
import com.picazodev.electroniclogistica.util.toKeyForProductMap
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class RepositoryImpl (
    val dataApi: DataApi,
    val dataSource: CombinationDatabase
) : Repository {


    /*
        Genera Maps de las listas de Product y Location
        con sus indices como parte de la Key
     */
    override suspend fun getLocationsMap(): Map<String, Location>{
        val locationsList = getLocationsList()
        val locationsMap = mutableMapOf<String, Location>()
        for (index in locationsList.indices){
            val key = index.toKeyForLocationMap()
            locationsMap[key] = locationsList[index]
        }
        return locationsMap
    }

    override suspend fun getProductsMap(): Map<String, Product>{
        val productsList = getProductsList()
        val productMap = mutableMapOf<String, Product>()
        for (index in productsList.indices){
            val key = index.toKeyForProductMap()
            productMap[key] = productsList[index]
        }
        return productMap

        this.getLocationsMap()
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


    override suspend fun addCombination(sortedProductIndexList: List<Int>){
        val locationList = getLocationsList()

        for (index in locationList.indices){
            val combination = Combination(
                index.toKeyForLocationMap(), sortedProductIndexList[index].toKeyForProductMap())

            dataSource.combinationDatabaseDao().insert(combination)
        }
    }

    override suspend fun getCombinatiosList(): List<Combination>? {
        return dataSource.combinationDatabaseDao().getAllCombination()
    }

}

