package com.picazodev.electroniclogistica.ui.locations

import com.picazodev.electroniclogistica.data.Location
import java.io.InputStream

interface LocationsView {

    //fun initializeApi(jsonData: InputStream)

    fun getLocationsList()

    fun viewLocationsList(list: List<Location>)

    fun setAptitudeText(aptitude: String)

    fun setErrorStatus()

    fun setDoneStatus(listOfSortedProductindices: MutableList<Int>)
    fun setLoadingStatus(locationMap: Map<String, Location>)
}