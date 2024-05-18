package com.alperenmengi.seyehatdefterim.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alperenmengi.seyehatdefterim.databinding.RecyclerRowBinding
import com.alperenmengi.seyehatdefterim.model.Travel
import com.alperenmengi.seyehatdefterim.view.AddActivity

class TravelAdapter(val travelList : ArrayList<Travel>) : RecyclerView.Adapter<TravelAdapter.TravelHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelHolder(binding)
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    override fun onBindViewHolder(holder: TravelHolder, position: Int) {
        holder.binding.nameText.text = travelList.get(position).name
        holder.itemView.setOnClickListener(){
            // basılınca yine AddActivity'e gidilecek
            val intent = Intent(holder.itemView.context, AddActivity::class.java)
            intent.putExtra("place", "TravelDetails")
            intent.putExtra("id", travelList.get(position).id)//seçilen yerin idsi
            holder.itemView.context.startActivity(intent)
        }
    }

    class TravelHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }
}