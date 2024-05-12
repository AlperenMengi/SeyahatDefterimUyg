package com.alperenmengi.seyehatdefterim.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alperenmengi.seyehatdefterim.model.Hotel
import com.alperenmengi.seyehatdefterim.model.Museum
import com.alperenmengi.seyehatdefterim.model.PlaceModel
import com.alperenmengi.seyehatdefterim.model.Travel

@Database(entities = ([Hotel::class, Museum::class, Travel::class]), version = 1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao() : PlaceDao // hep aynı işlemleri yapacağımız için sadece 1 adet Dao oluşturdum.
}
