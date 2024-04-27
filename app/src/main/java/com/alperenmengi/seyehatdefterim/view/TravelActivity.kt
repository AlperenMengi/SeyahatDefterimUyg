package com.alperenmengi.seyehatdefterim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperenmengi.seyehatdefterim.Adapter.TravelAdapter
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.ActivityTravelBinding
import com.alperenmengi.seyehatdefterim.model.PlaceModel

class TravelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTravelBinding
    private lateinit var travelList : ArrayList<PlaceModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        travelList = ArrayList<PlaceModel>()

        //veri tabanından verileri okuma
        try{
            val database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM travel", null)
            val hotelNameIx = cursor.getColumnIndex("travelName")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val name = cursor.getString(hotelNameIx)
                val id = cursor.getInt(idIx)
                val hotel = PlaceModel(name, id)
                travelList.add(hotel)
            }
            cursor.close()

        }catch(e : Exception){
            e.printStackTrace()
        }

        binding.recyclerViewTravel.layoutManager = LinearLayoutManager(this@TravelActivity)
        val travelAdapter = TravelAdapter(travelList)
        binding.recyclerViewTravel.adapter = travelAdapter
    }

    //Menü işlemleri

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.travel_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_travel){
            val intent = Intent(this@TravelActivity, AddActivity::class.java)
            intent.putExtra("place", "travel")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}