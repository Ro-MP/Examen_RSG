package com.picazodev.electroniclogistica.data.remote

import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product


data class DataProperty(

    val products: List<Product>,
    val locations: List<Location>

)






