package com.picazodev.electroniclogistica.ui.productdetail

import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product
import com.picazodev.electroniclogistica.data.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProductDetailPresenterImpl(
    val repository: Repository,
) : ProductDetailPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // Evita que si una corrutina lanza una excepcion las demás no se cancelen
    private var job = SupervisorJob()


    //TODO Inicializar variables
    private lateinit var productDetailView: ProductDetailView
    private lateinit var locationKey: String
    private lateinit var productKey: String


    var locationMap = mapOf<String, Location>()
    var productMap = mapOf<String, Product>()

    lateinit var locationName: String
    lateinit var locationType: String
    lateinit var productName: String
    lateinit var productWeigth: String
    lateinit var productVolume: String


    init {
        setLocationMap()
        setProductMap()
    }

    override fun printData() {
        productDetailView.apply {
            this.printLocationName(locationName)
            this.printLocationType(locationType)
            this.printProductName(productName)
            this.printProductWeigth(productWeigth)
            this.printProductVolume(productVolume)
            this.unableLoadingImageVisibility()
        }
    }

    override fun initializeViewInfo(
        productDetailView: ProductDetailFragment,
        locationKey: String,
        productKey: String) {

        this.productDetailView = productDetailView
        this.locationKey = locationKey
        this.productKey = productKey

    }


    override fun initializeVariables(){
        locationName = locationMap[locationKey]?.name ?: ""
        locationType = locationMap[locationKey]?.type ?: ""
        productName = productMap[productKey]?.name ?: ""
        productWeigth = productMap[productKey]?.weight.toString() ?: ""
        productVolume = productMap[productKey]?.volume.toString() ?: ""
        printData()
    }

    override fun setLocationMap(){
        locationMap = getLocationsMap()
    }

    override fun setProductMap(){
        productMap = getProductsMap()
    }

    override fun getLocationsMap(): Map<String, Location>{
        val locationsMap = mutableMapOf<String, Location>()
        launch {
            locationsMap.putAll(
                withContext(Dispatchers.IO){
                    repository.getLocationsMap()
                })
        }
        return locationsMap
    }

    override fun getProductsMap():Map<String, Product>{
        val productsMap = mutableMapOf<String, Product>()
        launch {
            productsMap.putAll(
                withContext(Dispatchers.IO){
                    repository.getProductsMap()
                })
            initializeVariables()
        }
        return productsMap
    }




}

