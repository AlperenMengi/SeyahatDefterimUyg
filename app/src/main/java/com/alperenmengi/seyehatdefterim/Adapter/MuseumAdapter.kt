package com.alperenmengi.seyehatdefterim.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alperenmengi.seyehatdefterim.databinding.RecyclerRowBinding
import com.alperenmengi.seyehatdefterim.model.HotelModel

class MuseumAdapter(val museumList : ArrayList<HotelModel>) : RecyclerView.Adapter<MuseumAdapter.MuseumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuseumHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MuseumHolder(binding)
    }

    override fun getItemCount(): Int {
        return museumList.size
    }

    override fun onBindViewHolder(holder: MuseumHolder, position: Int) {
        holder.binding.nameText.text = museumList.get(position).name
        holder.itemView.setOnClickListener(){
            // go to detail activity
        }
    }

    class MuseumHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }


}