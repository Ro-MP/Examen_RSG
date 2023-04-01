package com.picazodev.electroniclogistica.ui.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.picazodev.electroniclogistica.R
import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.data.local.CombinationDatabase
import com.picazodev.electroniclogistica.databinding.FragmentLocationsBinding
import com.picazodev.electroniclogistica.toKeyForLocationMap
import com.picazodev.electroniclogistica.toKeyForProductMap


class LocationsFragment : Fragment(), LocationsView {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!


    private lateinit var locationsPresenter : LocationsPresenterImpl

    private lateinit var clickLocationLambda : (locationIndex: Int) -> Unit



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val view = binding.root


        val application = requireNotNull(this.activity).application
        val dataSource = CombinationDatabase.getInstance(application).combinationDatabaseDao

        val jsonData = resources.openRawResource(R.raw.data)
        locationsPresenter = LocationsPresenterImpl(this, jsonData, dataSource)

        getLocationsList()





        return view
    }







    override fun getLocationsList() {
        with(locationsPresenter){
            getLocationsList()
            setLocationMap()
            setProductMap()
        }

    }


    override fun viewLocationsList(list: List<Location>) {

        val adapter = LocationsAdapter(LocationListener { locationName ->

            clickLocationLambda(locationName)
        })
        val manager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        binding.rvLocationsList.apply {
            this.adapter = adapter
            this.layoutManager = manager
        }


        adapter.submitList(list)

    }

    fun dataStatus(status: CombinationsApiStatus){
        with(binding.imgLoading){
            when(status){
                CombinationsApiStatus.LOADING -> {
                    this.visibility = View.VISIBLE
                    this.setImageResource(R.drawable.loading_animation)
                    binding.aptitudeValue.visibility = View.GONE
                    clickLocationLambda = { locationIndex ->
                        val locationName = locationsPresenter.locationMap[locationIndex.toKeyForLocationMap()]?.name
                        val text = "Cargando datos, por favor intenta ingresar a $locationName " +
                                "en unos segundos"
                        Toast.makeText(context, text, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                CombinationsApiStatus.DONE -> {
                    this.visibility = View.GONE
                    binding.aptitudeValue.visibility = View.VISIBLE
                    clickLocationLambda = {locationIndex ->
                        val productIndex = locationsPresenter.listOfSortedProductindices[locationIndex]
                        this@LocationsFragment.findNavController().navigate(LocationsFragmentDirections
                            .actionUbicationsFragmentToProductDetailFragment(
                                locationIndex.toKeyForLocationMap(), productIndex.toKeyForProductMap()))
                    }
                }
                CombinationsApiStatus.ERROR -> {
                    this.visibility = View.VISIBLE
                    binding.aptitudeValue.visibility = View.GONE
                    this.setImageResource(R.drawable.ic_heart_broken)
                    clickLocationLambda = { locationName ->
                        val text = "Error existente, revisa tu señal de internet"
                        Toast.makeText(context, text, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    }

    fun setAptitudeText(aptitude: String){
        binding.aptitudeValue.text = aptitude
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        locationsPresenter.cancelJob()
    }
}