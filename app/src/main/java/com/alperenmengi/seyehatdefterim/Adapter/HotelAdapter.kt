package com.alperenmengi.seyehatdefterim.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alperenmengi.seyehatdefterim.databinding.RecyclerRowBinding
import com.alperenmengi.seyehatdefterim.model.PlaceModel

class HotelAdapter(val hotelList : ArrayList<PlaceModel>) : RecyclerView.Adapter<HotelAdapter.HotelHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotelHolder(binding)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    override fun onBindViewHolder(holder: HotelHolder, position: Int) {
        holder.binding.nameText.text = hotelList.get(position).name
    }

    class HotelHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }

}