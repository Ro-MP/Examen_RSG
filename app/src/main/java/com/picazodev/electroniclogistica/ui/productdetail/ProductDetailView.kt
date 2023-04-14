package com.picazodev.electroniclogistica.ui.productdetail

interface ProductDetailView {

    fun initializeViewInfoInPresenter(locationKey: String, productKey: String)

    fun printLocationName(locationName: String)

    fun printLocationType(locationType: String)

    fun printProductName(productName: String)

    fun printProductWeigth(productWeigth: String)

    fun printProductVolume(productVolume: String)

    fun unableLoadingImageVisibility()
}