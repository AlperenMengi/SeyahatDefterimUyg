package com.alperenmengi.seyehatdefterim.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alperenmengi.seyehatdefterim.model.Hotel
import com.alperenmengi.seyehatdefterim.model.Museum
import com.alperenmengi.seyehatdefterim.model.PlaceModel
import com.alperenmengi.seyehatdefterim.model.Travel

@Dao
interface PlaceDao {

    //Hotel
    @Query("SELECT * FROM Hotel")
    fun getAllHotels(): List<Hotel>

    @Query("SELECT * FROM Hotel where id = :selectedId")
    fun getSelectedHotel(selectedId: Int): List<Hotel>

    @Insert
    fun insertHotel(hotel: Hotel)

    @Delete
    fun deleteHotel(hotel: Hotel)

    //Museum
    @Query("SELECT * FROM Museum")
    fun getAllMuseums() : List<Museum>

    @Query("SELECT * FROM Museum where id = :selectedId")
    fun getSelectedMuseum(selectedId: Int): List<Museum>

    @Insert
    fun insertMuseum(museum : Museum)

    @Delete
    fun deleteMuseum(museum : Museum)

    //Travel
    @Query("SELECT * FROM Travel")
    fun getAllTravels() : List<Travel>

    @Query("SELECT * FROM Travel where id = :selectedId")
    fun getSelectedTravel(selectedId: Int): List<Travel>

    @Insert
    fun insertTravel(travel : Travel)

    @Delete
    fun deleteTravel(travel : Travel)


}