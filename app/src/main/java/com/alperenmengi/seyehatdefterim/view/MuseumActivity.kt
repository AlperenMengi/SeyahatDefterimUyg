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
import com.alperenmengi.seyehatdefterim.model.PlaceModel

class MuseumActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMuseumBinding
    private lateinit var museumList : ArrayList<PlaceModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMuseumBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        museumList = ArrayList<PlaceModel>()

        //veri tabanından verileri okuma
        try{
            val database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM museum", null)
            val hotelNameIx = cursor.getColumnIndex("museumName")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val name = cursor.getString(hotelNameIx)
                val id = cursor.getInt(idIx)
                val hotel = PlaceModel(name, id)
                museumList.add(hotel)
            }
            cursor.close()

        }catch(e : Exception){
            e.printStackTrace()
        }

        binding.recyclerViewMuseum.layoutManager = LinearLayoutManager(this@MuseumActivity)
        val museumAdapter = MuseumAdapter(museumList)
        binding.recyclerViewMuseum.adapter = museumAdapter

    }

    //Menü işlemleri
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