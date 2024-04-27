package com.alperenmengi.seyehatdefterim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperenmengi.seyehatdefterim.Adapter.MuseumAdapter
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.ActivityMuseumBinding

class MuseumActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMuseumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMuseumBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.recyclerViewMuseum.layoutManager = LinearLayoutManager(this@MuseumActivity)
        //val museumAdapter = MuseumAdapter(museumList)
        //binding.recyclerViewMuseum.adapter = museumAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.museum_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_museum){
            val intent = Intent(this@MuseumActivity, AddActivity::class.java)
            intent.putExtra("place", "museum")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}