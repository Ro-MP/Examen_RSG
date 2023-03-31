package com.picazodev.electroniclogistica.ui.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picazodev.electroniclogistica.data.Location
import com.picazodev.electroniclogistica.databinding.LocationItemViewBinding

class LocationsAdapter(val clickListener: LocationListener) : ListAdapter<Location, LocationsAdapter.ViewHolder>(LocationDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, position)
    }

    override fun getItemCount() = currentList.size



    class ViewHolder private constructor(val binding: LocationItemViewBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Location, clickListener: LocationListener, index: Int) {
            binding.tvLocationName.text = item.name
            binding.tvLocationType.text = item.type
            binding.cardViewItem.setOnClickListener {
                clickListener.onClick(index)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LocationItemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}


class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

}

class LocationListener(val clickListener: (locationIndex: Int) -> Unit){
    fun onClick(index: Int) = clickListener(index)
}