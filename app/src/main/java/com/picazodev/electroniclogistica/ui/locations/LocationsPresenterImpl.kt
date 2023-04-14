package com.picazodev.electroniclogistica.ui.locations


import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.Product
import com.picazodev.electroniclogistica.data.Repository
import com.picazodev.electroniclogistica.data.RepositoryImpl
import com.picazodev.electroniclogistica.domain.LocationAlgorithm
import kotlinx.coroutines.*
import java.io.InputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

enum class CombinationsApiStatus { LOADING, ERROR, DONE}

class LocationsPresenterImpl(
    val repository : Repository,
    val locationAlgorithm : LocationAlgorithm
) : LocationsPresenter, CoroutineScope {

    private lateinit var ubicationsView: LocationsView

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // Evita que si una corrutina lanza una excepcion las demás no se cancelen
    private var job = SupervisorJob()


    //@Inject lateinit var repository : RepositoryImpl

    //@Inject lateinit var locationAlgorithm: LocationAlgorithm

    var combinationsStatus = CombinationsApiStatus.LOADING

    var locationMap = mapOf<String, Location>()
    var productMap = mapOf<String, Product>()

    var listOfSortedProductindices = mutableListOf<Int>()



    init {


    }


    override fun onViewAttached(view: LocationsView){
        this.ubicationsView = view
    }

    override fun initializeApi(jsonData: InputStream) {

    }

    override fun getListOfSortedProductindices(){
        launch {
             val indices = withContext(Dispatchers.Default){
                locationAlgorithm.getListOfProductIndex()
            }
            listOfSortedProductindices.addAll(indices)
        }
    }

    override fun getMaximumAptitude(){

        val tInicio = System.currentTimeMillis()
        launch(){
            try {
                ubicationsView.setLoadingStatus(locationMap)
                combinationsStatus = CombinationsApiStatus.LOADING

                val maximumAptitude = withContext(Dispatchers.Default){
                    locationAlgorithm.getMaximumAptitude()
                }

                println("# # # -  Aptitud Máxima: $maximumAptitude  - # # #")
                println("# # # -  Numero de posibles combinaciones encontradas: ${locationAlgorithm.numeroDeIteraciones}  - # # #")
                val tFinal = System.currentTimeMillis()
                val tDiferencia = tFinal - tInicio
                println("# # # -  Tiempo transcurrido: ${tDiferencia/1000.0} seg - # # #")

                ubicationsView.setDoneStatus(listOfSortedProductindices)
                combinationsStatus = CombinationsApiStatus.DONE
                ubicationsView.setAptitudeText(maximumAptitude.toString())

                getListOfSortedProductindices()
            } catch (e: Exception){
                ubicationsView.setErrorStatus()
                combinationsStatus = CombinationsApiStatus.ERROR
                println("# # # -  Exception: $e - # # #")

            }

        }

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