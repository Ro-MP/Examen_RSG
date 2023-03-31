package com.picazodev.electroniclogistica.ui.locations


import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product
import com.picazodev.electroniclogistica.data.RepositoryImpl
import com.picazodev.electroniclogistica.data.remote.DataApi
import com.picazodev.electroniclogistica.domain.LocationAlgorithm
import kotlinx.coroutines.*
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

enum class CombinationsApiStatus { LOADING, ERROR, DONE}

class LocationsPresenterImpl(
    private val ubicationsView: LocationsFragment,
    private val jsonData: InputStream
) : LocationsPresenter, CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // Evita que si una corrutina lanza una excepcion las demás no se cancelen
    private var job = SupervisorJob()

    val apiInstance : DataApi = DataApi.getInstance(jsonData)
    val repository : RepositoryImpl = RepositoryImpl(apiInstance)
    val locationAlgorithm = LocationAlgorithm(repository)

    var combinationsStatus = CombinationsApiStatus.LOADING

    var locationMap = mapOf<String, Location>()
    var productMap = mapOf<String, Product>()

    var listOfSortedProductindices = mutableListOf<Int>()



    init {
        getMaximumAptitude()

    }

    override fun initializeApi(jsonData: InputStream) {

    }

    fun getListOfSortedProductindices(){
        launch {
             val indices = withContext(Dispatchers.Default){
                locationAlgorithm.getListOfProductIndex()
            }
            listOfSortedProductindices.addAll(indices)
        }
    }

    fun getMaximumAptitude(){

        val tInicio = System.currentTimeMillis()
        launch(){
            try {
                ubicationsView.dataStatus(CombinationsApiStatus.LOADING)
                combinationsStatus = CombinationsApiStatus.LOADING

                val maximumAptitude = withContext(Dispatchers.Default){
                    locationAlgorithm.getMaximumAptitude()
                }
                println("# # # -  Aptitud Máxima: $maximumAptitude  - # # #")
                println("# # # -  Numero de posibles combinaciones encontradas: ${locationAlgorithm.numeroDeIteraciones}  - # # #")
                val tFinal = System.currentTimeMillis()
                val tDiferencia = tFinal - tInicio
                println("# # # -  Tiempo transcurrido: ${tDiferencia/1000.0} seg - # # #")
                ubicationsView.dataStatus(CombinationsApiStatus.DONE)
                combinationsStatus = CombinationsApiStatus.DONE
                ubicationsView.setAptitudeText(maximumAptitude.toString())

                getListOfSortedProductindices()
            } catch (e: Exception){
                ubicationsView.dataStatus(CombinationsApiStatus.ERROR)
                combinationsStatus = CombinationsApiStatus.ERROR
                println("# # # -  Exception: $e - # # #")

            }

        }

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
        }
        return productsMap
    }


    override fun getLocationsList() {
        var locationsList : List<Location>? = listOf()

        launch {
            locationsList = withContext(Dispatchers.IO) {
                repository.getLocationsList()
            }
            locationsList?.apply {
                ubicationsView.viewLocationsList(this)
            }
        }
    }



    // Si la activity muere, ya no se actualizará la UI
    override fun cancelJob() {
        job.cancel()
    }



}