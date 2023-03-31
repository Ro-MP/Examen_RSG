package com.picazodev.electroniclogistica.domain

import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product
import com.picazodev.electroniclogistica.data.RepositoryImpl

class LocationAlgorithm(val repository: RepositoryImpl) {


    private lateinit var locationsList : List<Location>
    private lateinit var productsList : List<Product>

    private val maximumAptitudeCombination = mutableListOf< List<Int> >()
    var numeroDeIteraciones = 0



    init {


    }


    suspend fun getListOfProductIndex(): List<Int> {
        if (maximumAptitudeCombination.isEmpty()){
            getLocations()
            getProducts()
            produceMaximumAptitude(maximumAptitudeCombination, getBSelectedToFalse())
        }

        //Genera una list con los index de productList, ordenados para maxima aptitud
        val sortedProductIndex = mutableListOf<Int>()
        maximumAptitudeCombination.forEach {
            // se asigna el index del product
            sortedProductIndex.add(it[1])
        }

        return sortedProductIndex as List<Int>
    }


    suspend fun getMaximumAptitude(): Double {
        if (maximumAptitudeCombination.isEmpty()){
            getLocations()
            getProducts()
            produceMaximumAptitude(maximumAptitudeCombination, getBSelectedToFalse())
        }

        return getTotalAptitude(maximumAptitudeCombination)


    }




    private suspend fun getLocations(){
        locationsList = repository.getLocationsList()
    }

    private suspend fun getProducts(){
        productsList = repository.getProductsList()
    }





    private fun getBSelectedToFalse(): MutableList<Boolean>{
        val bSelected = mutableListOf<Boolean>()
        repeat(productsList.size){
            bSelected.add(false)
        }
        return bSelected
    }


    private fun produceMaximumAptitude(combination: List<List<Int>>, bSeleccionados: MutableList<Boolean>, indexLocation: Int = 0){

        val bSelected = bSeleccionados
        val combinationIndexList = mutableListOf< List<Int> >()
        combinationIndexList.addAll(combination)


        if (indexLocation < bSelected.size){

            for(indexProduct in productsList.indices){

                if (!bSelected[indexProduct]){
                    bSelected[indexProduct] = true

                    val combinationIndexList2 = mutableListOf< List<Int> >()
                    combinationIndexList2.addAll(combinationIndexList)
                    combinationIndexList2.add(listOf(indexLocation, indexProduct))

                    //var texto2 = texto + "[${A[indexA]} - ${B[indexB]}] / "

                    if (indexLocation < locationsList.size-1){
                        produceMaximumAptitude(combinationIndexList2, bSelected, indexLocation + 1)
                    } else{

                        numeroDeIteraciones ++
                        if (maximumAptitudeCombination.isEmpty()){
                            maximumAptitudeCombination.addAll(combinationIndexList2)
                        }else{

                            if (getTotalAptitude(combinationIndexList2) > getTotalAptitude(maximumAptitudeCombination)){
                                maximumAptitudeCombination.clear()
                                maximumAptitudeCombination.addAll(combinationIndexList2)
                            }

                        }
                    }

                    bSelected[indexProduct] = false
                }



            }
        }

    }


    private fun getTotalAptitude(combination: List< List<Int> >): Double{
        var totalSs = 0.0
        combination.forEach {
            totalSs += getLocationAptitude(locationsList[it[0]], productsList[it[1]])
        }

        return totalSs
    }

    private fun getLocationAptitude(location: Location, product: Product): Double{
        var ss = 0.0
        if (product.weight % 2 == 0){
            ss = location.name.length * 1.5
        } else {
            ss = location.type.length * 1.0
        }

        if (product.volume == location.name.length){
            ss *= 1.5
        }

        return ss
    }

}

