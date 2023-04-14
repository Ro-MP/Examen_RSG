package com.picazodev.electroniclogistica.ui.productdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picazodev.electroniclogistica.R
import com.picazodev.electroniclogistica.data.local.CombinationDatabase
import com.picazodev.electroniclogistica.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProductDetailFragment : Fragment(), ProductDetailView {


    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var productDetailPresenter: ProductDetailPresenter


    private lateinit var args: ProductDetailFragmentArgs
    private var bundle: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        bundle = savedInstanceState
        args = ProductDetailFragmentArgs.fromBundle(requireArguments())


        initializeViewInfoInPresenter(args.locationKey, args.productKey)


        return view
    }




    override fun initializeViewInfoInPresenter(locationKey: String, productKey: String) {
        productDetailPresenter.initializeViewInfo(this, locationKey, productKey)
    }



    override fun printLocationName(locationName: String) {
        binding.tvLocationName.text = locationName
    }

    override fun printLocationType(locationType: String) {
        binding.tvLocationType.text = locationType
    }

    override fun printProductName(productName: String) {
        binding.tvProductName.text = productName
    }

    override fun printProductWeigth(productWeigth: String) {
        val weightText = "Weigth: $productWeigth"
        binding.tvProductWeight.text = weightText
    }

    override fun printProductVolume(productVolume: String) {
        val volumeText = "Volume: $productVolume"
        binding.tvProductVolume.text = volumeText
    }

    override fun unableLoadingImageVisibility() {
        binding.imgLoading.visibility = View.GONE
    }




    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        bundle?.apply {
            outState.putAll(this)
        }
    }




}