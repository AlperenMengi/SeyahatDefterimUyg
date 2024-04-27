package com.alperenmengi.seyehatdefterim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperenmengi.seyehatdefterim.Adapter.HotelAdapter
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.ActivityHotelBinding
import com.alperenmengi.seyehatdefterim.model.PlaceModel

class HotelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHotelBinding
    private lateinit var hotelList : ArrayList<PlaceModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hotelList = ArrayList<PlaceModel>()

        //veri tabanından verileri okuma
        try{
            val database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM hotel", null)
            val hotelNameIx = cursor.getColumnIndex("hotelName")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val name = cursor.getString(hotelNameIx)
                val id = cursor.getInt(idIx)
                val hotel = PlaceModel(name, id)
                hotelList.add(hotel)
            }
            cursor.close()

        }catch(e : Exception){
            e.printStackTrace()
        }

        binding.recyclerViewHotel.layoutManager = LinearLayoutManager(this@HotelActivity)
        val hotelAdapter =HotelAdapter(hotelList)
        binding.recyclerViewHotel.adapter = hotelAdapter


    }

    //Menü işlemleri
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addHotel){
            val intent = Intent(this@HotelActivity, AddActivity::class.java)
            intent.putExtra("place", "hotel")
            finish()
            startActivity(intent)

        }
        else Toast.makeText(this, "ife girmedi", Toast.LENGTH_LONG).show()

        return super.onOptionsItemSelected(item)
    }

}