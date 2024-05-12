package com.alperenmengi.seyehatdefterim.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alperenmengi.seyehatdefterim.databinding.RecyclerRowBinding
import com.alperenmengi.seyehatdefterim.model.Museum
import com.alperenmengi.seyehatdefterim.model.PlaceModel
import com.alperenmengi.seyehatdefterim.view.AddActivity

class MuseumAdapter(val museumList : ArrayList<Museum>) : RecyclerView.Adapter<MuseumAdapter.MuseumHolder>() {

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
            val intent = Intent(holder.itemView.context, AddActivity::class.java)
            intent.putExtra("place", "MuseumDetails")
            intent.putExtra("id", museumList.get(position).id)//seçilen yerin idsi
            holder.itemView.context.startActivity(intent)

        }
    }

    class MuseumHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }


}