package com.picazodev.electroniclogistica.ui.locations

import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product
import java.io.InputStream

interface LocationsPresenter {

    fun initializeApi(jsonData: InputStream)

    fun getLocationsList()

    fun setLocationMap()

    fun setProductMap()

    fun cancelJob()

    fun getListOfSortedProductindices()

    fun getMaximumAptitude()

    fun getLocationsMap(): Map<String, Location>

    fun getProductsMap():Map<String, Product>

    fun onViewAttached(view: LocationsView)

}