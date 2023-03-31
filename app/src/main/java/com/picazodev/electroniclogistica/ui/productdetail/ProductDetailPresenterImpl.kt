package com.picazodev.electroniclogistica.ui.productdetail

import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product
import com.picazodev.electroniclogistica.data.RepositoryImpl
import com.picazodev.electroniclogistica.data.remote.DataApi
import kotlinx.coroutines.*
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

class ProductDetailPresenterImpl(
    private val productDetailView: ProductDetailFragment,
    private val locationKey: String,
    private val productKey: String,
    private val jsonData: InputStream
) : ProductDetailPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // Evita que si una corrutina lanza una excepcion las demás no se cancelen
    private var job = SupervisorJob()


    val apiInstance : DataApi = DataApi.getInstance(jsonData)
    val repository : RepositoryImpl = RepositoryImpl(apiInstance)

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

    fun initializeVariables(){
        locationName = locationMap[locationKey]?.name ?: ""
        locationType = locationMap[locationKey]?.type ?: ""
        productName = productMap[productKey]?.name ?: ""
        productWeigth = productMap[productKey]?.weight.toString() ?: ""
        productVolume = productMap[productKey]?.volume.toString() ?: ""
        productDetailView.printData()
    }

    fun setLocationMap(){
        locationMap = getLocationsMap()
    }

    fun setProductMap(){
        productMap = getProductsMap()
    }

    fun getLocationsMap(): Map<String, Location>{
        val locationsMap = mutableMapOf<String, Location>()
        launch {
            locationsMap.putAll(
                withContext(Dispatchers.IO){
                    repository.getLocationsMap()
                })
        }
        return locationsMap
    }

    fun getProductsMap():Map<String, Product>{
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

