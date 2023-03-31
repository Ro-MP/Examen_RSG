package com.picazodev.electroniclogistica.ui.productdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picazodev.electroniclogistica.R
import com.picazodev.electroniclogistica.databinding.FragmentProductDetailBinding


class ProductDetailFragment : Fragment(), ProductDetailView {


    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var productDetailPresenter: ProductDetailPresenterImpl


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = ProductDetailFragmentArgs.fromBundle(requireArguments())
        val jsonData = resources.openRawResource(R.raw.data)

        productDetailPresenter = ProductDetailPresenterImpl(this, args.locationKey, args.productKey, jsonData)






        return view
    }


    fun printData(){
        with(productDetailPresenter){
            binding.tvLocationName.text = this.locationName
            binding.tvLocationType.text = this.locationType
            binding.tvProductName.text = this.productName
            val weightText = "Weigth: ${this.productWeigth}"
            binding.tvProductWeight.text = weightText
            val volumeText = "Volume: ${this.productVolume}"
            binding.tvProductVolume.text = volumeText
            binding.imgLoading.visibility = View.GONE
        }
    }

}