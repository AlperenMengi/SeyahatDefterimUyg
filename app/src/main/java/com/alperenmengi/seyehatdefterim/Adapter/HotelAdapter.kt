package com.alperenmengi.seyehatdefterim.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.RecyclerRowBinding
import com.alperenmengi.seyehatdefterim.model.Hotel
import com.alperenmengi.seyehatdefterim.view.AddActivity

class HotelAdapter(val hotelList : ArrayList<Hotel>) : RecyclerView.Adapter<HotelAdapter.HotelHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotelHolder(binding)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    override fun onBindViewHolder(holder: HotelHolder, position: Int) {
        holder.binding.nameText.text = hotelList.get(position).name
        holder.binding.icon.setImageResource(R.drawable.click)
        holder.binding.cardView.setOnClickListener(){
            val intent = Intent(holder.itemView.context, AddActivity::class.java)
            intent.putExtra("place", "HotelDetails")
            intent.putExtra("id", hotelList.get(position).id)//seçilen yerin idsi
            holder.itemView.context.startActivity(intent)
        }
    }

    class HotelHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }

}