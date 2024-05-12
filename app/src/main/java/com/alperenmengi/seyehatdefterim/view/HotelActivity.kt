package com.alperenmengi.seyehatdefterim.view

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.alperenmengi.seyehatdefterim.Adapter.HotelAdapter
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.ActivityHotelBinding
import com.alperenmengi.seyehatdefterim.model.Hotel
import com.alperenmengi.seyehatdefterim.model.PlaceModel
import com.alperenmengi.seyehatdefterim.roomdb.PlaceDao
import com.alperenmengi.seyehatdefterim.roomdb.PlaceDatabase

class HotelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHotelBinding
    private lateinit var hotelList : ArrayList<Hotel>
    private var hotelAdapter: HotelAdapter? = null

    //database işlemleri için
    private lateinit var db : PlaceDatabase
    private lateinit var placeDao : PlaceDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hotelList = ArrayList<Hotel>()

        db = Room.databaseBuilder(
            applicationContext,
            PlaceDatabase::class.java, "PlacesV2"
        ).allowMainThreadQueries().build()

        placeDao = db.placeDao()

        //veri tabanından verileri okuma
        try{
            hotelList = placeDao.getAllHotels() as ArrayList<Hotel>
            hotelAdapter!!.notifyDataSetChanged() // veri seti değiştiği anda adaptere kendini güncelle diye haber veriyoruz.

            /*val database = this.openOrCreateDatabase("Place", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM hotel", null)
            val idIx = cursor.getColumnIndex("id")
            val hotelNameIx = cursor.getColumnIndex("hotelName")
            val tagIx = cursor.getColumnIndex("")
            val securityIx = cursor.getColumnIndex("id")
            val descriptionIx = cursor.getColumnIndex("id")
            val latitudeIx = cursor.getColumnIndex("id")
            val longitudeIx = cursor.getColumnIndex("id")
            val imageIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val name = cursor.getString(hotelNameIx)
                val id = cursor.getInt(idIx)
                val hotel = Hotel(name, id)
                hotelList.add(hotel)
            }
            cursor.close()*/

        }catch(e : Exception){
            e.printStackTrace()
        }

        binding.recyclerViewHotel.layoutManager = LinearLayoutManager(this@HotelActivity)
        hotelAdapter = HotelAdapter(hotelList)
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
        return super.onOptionsItemSelected(item)
    }

}