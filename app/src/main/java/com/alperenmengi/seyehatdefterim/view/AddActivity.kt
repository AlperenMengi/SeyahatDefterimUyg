package com.alperenmengi.seyehatdefterim.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Snackbar
import androidx.compose.ui.text.toUpperCase
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.ActivityAddBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.util.Locale

//<style name="Theme.SeyehatDefterim" parent="Base.Theme.SeyehatDefterim" />

class AddActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBinding
    private lateinit var tagSpinnerList : ArrayList<String>
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent> // galeriye gitmek için kullancağız
    private lateinit var permissionLauncher: ActivityResultLauncher<String> // izini istemek için kullanacağız
    private var place : String? = null
    private var place2 : String? = null
    private var selectedLatitude : String? = null
    private var selectedLongitude : String? = null
    private var selectedBitmap : Bitmap? = null
    private var choosenTag : String? = null
    private var isLocationChoosed : Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fillTagSpinner() // Etiket Listesi dolduruldu
        fillSecuritySpinner() // Güvenlik Listesi dolduruldu
        registerLauncher()

        place = intent.getStringExtra("place").toString() // Hotel, Museum, Travel'den gelen intent
        place2 = intent.getStringExtra("place2") // MapsActivity'den gelen intent
        println("Mapsten gelen place: " + place2)
        //Toast.makeText(this, place.toUpperCase(Locale("tr")), Toast.LENGTH_LONG ).show()
        selectedLatitude = intent.getStringExtra("latitude")
        selectedLongitude = intent.getStringExtra("longitude")
        isLocationChoosed = intent.getBooleanExtra("isChoosed", false)

        if (isLocationChoosed!!){
            binding.locationButton.text = "Konum Seçildi"
            binding.locationButton.isEnabled = false
        }


        println("latitude: " + selectedLatitude +  ", longitude: " + selectedLongitude)


    }

    fun kaydet(view: View) {

        val placeName = binding.nameText.text.toString()
        val placeTag = binding.tagSpinner.selectedItem.toString()
        val placeSecurity = binding.securitySpinner.selectedItem.toString()
        val placeDescription = binding.descriptionText.text.toString()
        val placeLatitude = selectedLatitude
        val placeLongitude = selectedLongitude
        //Toast.makeText(this, " ${placeName} - " + "${placeTag} - " + "${placeSecurity} - " + "${placeDescription}", Toast.LENGTH_LONG).show()
        if (selectedBitmap != null){

            val smallBitmap = makeSmallerBitmap(selectedBitmap!!, 300)
            //görseli byte dizisine çevirerek veri tabanına kayıt ederiz
            val byteArray = convertPhotoToByte(smallBitmap)

            // veri tabanı işlemleri try-catch içinde
            try {
                Toast.makeText(this, place2, Toast.LENGTH_LONG).show()
                val database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null)
                if (place == "hotel" || place2 == "hotel"){
                    database.execSQL("CREATE TABLE IF NOT EXISTS hotel (id INTEGER PRIMARY KEY, hotelName VARCHAR, tag VARCHAR, security VARCHAR, description VARCHAR, latitude VARCHAR, longitude VARCHAR , image BLOB)")
                    val sqlString = "INSERT INTO hotel (hotelName, tag, security, description, latitude, longitude, image) VALUES (?, ?, ?, ?, ?, ?, ?)"
                    val statement = database.compileStatement(sqlString)
                    statement.bindString(1, placeName)
                    statement.bindString(2, placeTag)
                    statement.bindString(3, placeSecurity)
                    statement.bindString(4, placeDescription)
                    statement.bindString(5, placeLatitude)
                    statement.bindString(6, placeLongitude)
                    statement.bindBlob(7, byteArray)
                    statement.execute()

                }
                if(place == "museum" || place2 == "museum"){
                   database.execSQL("CREATE TABLE IF NOT EXISTS museum (id INTEGER PRIMARY KEY, museumName VARCHAR, tag VARCHAR, security VARCHAR, description VARCHAR, latitude VARCHAR, longitude VARCHAR, image BLOB)")
                    val sqlString = "INSERT INTO museum (museumName, tag, security, description, latitude, longitude, image) VALUES (?, ?, ?, ?, ?, ?, ?)"
                    val statement = database.compileStatement(sqlString)
                    statement.bindString(1, placeName)
                    statement.bindString(2, placeTag)
                    statement.bindString(3, placeSecurity)
                    statement.bindString(4, placeDescription)
                    statement.bindString(5, placeLatitude)
                    statement.bindString(6, placeLongitude)
                    statement.bindBlob(7, byteArray)
                    statement.execute()

                }
                if(place == "travel" || place2 == "travel"){
                    database.execSQL("CREATE TABLE IF NOT EXISTS travel (id INTEGER PRIMARY KEY, travelName VARCHAR, tag VARCHAR, security VARCHAR, description VARCHAR, latitude VARCHAR, longitude VARCHAR, image BLOB)")
                    val sqlString = "INSERT INTO travel (travelName, tag, security, description, latitude, longitude, image) VALUES (?, ?, ?, ?, ?, ?, ?)"
                    val statement = database.compileStatement(sqlString)
                    statement.bindString(1, placeName)
                    statement.bindString(2, placeTag)
                    statement.bindString(3, placeSecurity)
                    statement.bindString(4, placeDescription)
                    statement.bindString(5, placeLatitude)
                    statement.bindString(6, placeLongitude)
                    statement.bindBlob(7, byteArray)
                    statement.execute()
                }

            }catch (e : Exception){
                e.printStackTrace()
            }
            if (place == "hotel" || place2 == "hotel"){
                val intent = Intent(this@AddActivity, HotelActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            if (place == "museum" || place2 == "museum"){
                val intent = Intent(this@AddActivity, MuseumActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            if (place == "travel" || place2 == "travel"){
                val intent = Intent(this@AddActivity, TravelActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }


    fun selectImage(view: View) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android API >= 33
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                    Snackbar.make(view, "Galeri İçin İzin Gerekli!", Snackbar.LENGTH_INDEFINITE).setAction("Galeri İzni Ver") {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        } else { // Android API <= 32
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view, "Galeri İçin İzin Gerekli!", Snackbar.LENGTH_INDEFINITE).setAction("Galeri İzni Ver") { //request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
                }else{
                    //request permisson
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }else{
                // go to gallery
                //intent yap
                val intentToGalley = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGalley)
            }
        }
    }// selectedImage sonu

    fun convertPhotoToByte(smallBitmap : Bitmap) : ByteArray{
        val outputStream = ByteArrayOutputStream()
        smallBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream) // görseli sıkıştırarak byte dizisine çeviririrz.
        val byteArray = outputStream.toByteArray() // görselin 0 ve 1 lere çevrilmiş hali
        return byteArray
    }

    private fun makeSmallerBitmap(image: Bitmap, maximumSize :Int) : Bitmap{
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toDouble() / height.toDouble()

        if (bitmapRatio > 1){
            //landscape
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()

        }else{
            //portrait
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)

    }

    private fun registerLauncher() {

       activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
           if (result.resultCode == RESULT_OK){
               val intentFromResult = result.data
               intentFromResult.let {
                   val imageData = it!!.data
                   imageData.let {
                       //binding.imageView.setImageURI(imageData) // veri tabanına kayıt yapacağımız için bize Bitmap hali lazım.
                       try{
                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                               val source  = ImageDecoder.createSource(this@AddActivity.contentResolver, imageData!!)
                               selectedBitmap = ImageDecoder.decodeBitmap(source)
                               binding.imageView.setImageBitmap(selectedBitmap)
                           } else {
                               selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageData!!)
                               binding.imageView.setImageBitmap(selectedBitmap)
                           }
                       }
                       catch (e : Exception){
                           println(e.printStackTrace())
                       }
                   }

               }
           }

       }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {result ->
            if (result){
                //permission granted
                val intentToGalley = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGalley)
            }
            else{
                //permission denied
                Toast.makeText(this@AddActivity, "Fotoğraf Seçimi İçin İzin Gerekli!", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun fillTagSpinner(){
        val tags = resources.getStringArray(R.array.Etiket)
        binding.tagSpinner.let { tagSpinner ->
            val tagAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tags)
            binding.tagSpinner.adapter = tagAdapter

            tagSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long,
                ) {
                    choosenTag = tags[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(this@AddActivity, "Lütfen Etiket için bir seçenek seçiniz!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun fillSecuritySpinner(){
        val security = resources.getStringArray(R.array.Güvenlik)
        binding.securitySpinner.let {
            val securityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, security)
            binding.securitySpinner.adapter = securityAdapter
        }
    }

    fun goToMaps(view: View) {
        val intent = Intent(this@AddActivity, MapsActivity::class.java)
        intent.putExtra("place", place)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}