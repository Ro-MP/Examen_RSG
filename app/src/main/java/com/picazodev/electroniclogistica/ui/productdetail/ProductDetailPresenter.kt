package com.picazodev.electroniclogistica.ui.productdetail

import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product

interface ProductDetailPresenter {


    fun initializeViewInfo(
        productDetailView: ProductDetailFragment,
        locationKey: String,
        productKey: String
    )
    fun initializeVariables()
    fun setLocationMap()
    fun setProductMap()
    fun getLocationsMap(): Map<String, Location>
    fun getProductsMap():Map<String, Product>


    fun printData()

}