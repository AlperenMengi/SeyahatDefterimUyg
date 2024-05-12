package com.alperenmengi.seyehatdefterim.roomdb

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alperenmengi.seyehatdefterim.model.Hotel

interface HotelDao {

    //Hotel
    @Query("SELECT * FROM Hotel")
    fun getAllHotels() : List<Hotel>

    @Insert
    fun insertHotel(hotel : Hotel)

    @Delete
    fun deleteHotel(hotel : Hotel)

}