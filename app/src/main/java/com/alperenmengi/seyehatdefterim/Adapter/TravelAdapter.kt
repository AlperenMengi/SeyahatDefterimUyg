package com.alperenmengi.seyehatdefterim.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alperenmengi.seyehatdefterim.databinding.RecyclerRowBinding
import com.alperenmengi.seyehatdefterim.model.PlaceModel

class TravelAdapter(val travelList : ArrayList<PlaceModel>) : RecyclerView.Adapter<TravelAdapter.TravelHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelHolder(binding)
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    override fun onBindViewHolder(holder: TravelHolder, position: Int) {
        holder.binding.nameText.text = travelList.get(position).name

    }

    class TravelHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }
}