package com.alperenmengi.seyehatdefterim.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Travel (

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "tag")
    val tag : String,

    @ColumnInfo(name = "security")
    val security : String,

    @ColumnInfo(name = "description")
    val description : String,

    @ColumnInfo(name = "latitude")
    val latitude : String?,

    @ColumnInfo(name = "longitude")
    val longitude : String?,

    @ColumnInfo(name = "image")
    val image : ByteArray) {

    @PrimaryKey(autoGenerate = true)
    var id  = 0

}