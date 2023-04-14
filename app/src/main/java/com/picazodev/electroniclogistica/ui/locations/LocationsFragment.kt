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
import com.picazodev.electroniclogistica.util.toKeyForLocationMap
import com.picazodev.electroniclogistica.util.toKeyForProductMap
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationsFragment : Fragment(), LocationsView {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var locationsPresenter: LocationsPresenter



    private lateinit var clickLocationLambda : (locationIndex: Int) -> Unit



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val view = binding.root




        locationsPresenter.onViewAttached(this)
        locationsPresenter.getMaximumAptitude()

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



    override fun setAptitudeText(aptitude: String){
        binding.aptitudeValue.text = aptitude
    }

    override fun setLoadingStatus(locationMap: Map<String, Location>) {
        with(binding.imgLoading){
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.loading_animation)
        }
        binding.aptitudeValue.visibility = View.GONE
        clickLocationLambda = { locationIndex ->
            val locationName = locationMap[locationIndex.toKeyForLocationMap()]?.name
            val text = "Cargando datos, por favor intenta ingresar a $locationName " +
                    "en unos segundos"
            Toast.makeText(context, text, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun setErrorStatus() {
        with(binding.imgLoading) {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.ic_heart_broken)
        }
        binding.aptitudeValue.visibility = View.GONE
        clickLocationLambda = { locationName ->
            val text = "Error existente, revisa tu señal de internet"
            Toast.makeText(context, text, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun setDoneStatus(listOfSortedProductindices: MutableList<Int>) {
        with(binding.imgLoading){
            this.visibility = View.GONE
        }
        binding.aptitudeValue.visibility = View.VISIBLE
        clickLocationLambda = {locationIndex ->
            val productIndex = listOfSortedProductindices[locationIndex]
            this@LocationsFragment.findNavController().navigate(LocationsFragmentDirections
                .actionUbicationsFragmentToProductDetailFragment(
                    locationIndex.toKeyForLocationMap(), productIndex.toKeyForProductMap()))
        }
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