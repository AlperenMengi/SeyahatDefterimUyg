package com.alperenmengi.seyehatdefterim.view

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alperenmengi.seyehatdefterim.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.alperenmengi.seyehatdefterim.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.snackbar.Snackbar

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager : LocationManager // konumla ilgili tüm işleri yapacak olan sınıf
    private lateinit var locationListener : LocationListener // konumda değişiklik olduğu zaman bunu algılayan ve bize haber veren arayüz
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedLatitude : Double? = null
    private var selectedLongitude : Double? = null
    private var latitudeFromSavedPlace : String? = null
    private var longitudeFromSavedPlace : String? = null
    private var nameFromSavedPlace : String? = null
    private var trackBoolean : Boolean? = null
    private var place : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        place = intent.getStringExtra("place")


        registerLauncher()

        selectedLatitude = 0.0
        selectedLongitude = 0.0
        sharedPreferences = this.getSharedPreferences("com.alperenmengi.seyehatdefterim.view", MODE_PRIVATE)
        trackBoolean = false// uygulama ilk açıldığında daha öncesinde kayıtlı bir konum olup olmadığı kontrolü




    }

    fun sendChoosenLocationToTheAddActivity(view : View){
        val intent = Intent(this@MapsActivity, AddActivity::class.java)
        intent.putExtra("latitude", selectedLatitude.toString())
        intent.putExtra("longitude", selectedLongitude.toString())
        intent.putExtra("isChoosed", true) // ilgili yerin seçildiğine işaret eden key
        intent.putExtra("place2", place)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this) // harita ve listener arasında bağlantı kurabilmek için buradaki işlemi gerçekteştiriyoruz.

        val intent = intent
        val info = intent.getStringExtra("info")

        if (info == "new"){
            locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
            locationListener = object : LocationListener{
                override fun onLocationChanged(location: Location) {
                    location.let {
                        trackBoolean = sharedPreferences.getBoolean("trackBoolean", false)
                        if (!trackBoolean!!){ // false ise bir öncesinde kayıtlı konum yoktur, konumu alıp true yapıyoruz.
                            val userLocation = LatLng(selectedLatitude!!, selectedLongitude!!)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                            sharedPreferences.edit().putBoolean("trackBoolean", true).apply()
                        }
                    }
                }
            }

            //Konum izni alma
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MapsActivity, Manifest.permission.ACCESS_FINE_LOCATION)){
                    Snackbar.make(binding.root, "Konum İzni Gerekli", Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver", object : OnClickListener{
                        override fun onClick(v: View?) {
                            // izin iste
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }).show()
                }else{
                    // izin iste
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }else{
                // konum bilgilerini al
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
                val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (lastLocation != null){
                    val lastUserLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15f))
                }
                mMap.isMyLocationEnabled = true // konum etkinleştirildi mi?, izni aldığımız için evet diyebiliyoruz.
            }

        }else if (info == "old") {
            mMap.clear()

            latitudeFromSavedPlace = intent.getStringExtra("savedLatitude")
            longitudeFromSavedPlace = intent.getStringExtra("savedLongitude")
            nameFromSavedPlace = intent.getStringExtra("savedPlaceName")

            binding.selectLocation.visibility = View.GONE

            if (latitudeFromSavedPlace != null && longitudeFromSavedPlace != null) {
                val savedLocation = LatLng(
                    latitudeFromSavedPlace!!.toDouble(),
                    longitudeFromSavedPlace!!.toDouble()
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(savedLocation, 15f))
                mMap.addMarker(MarkerOptions().position(savedLocation).title(nameFromSavedPlace.toString()))
            }
        }
    }

    fun registerLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {result ->
            if (result){
                //izin verildi
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
                    val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastLocation != null){
                        val lastUserLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15f))
                    }
                    mMap.isMyLocationEnabled = true // konum etkinleştirildi mi?, izni aldığımız için evet diyebiliyoruz.
                }
            }else{
                //izin verilmedi
                Toast.makeText(this, "Konumunuzun alınabilmesi için izin verilmesi gerekmektedir!", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onMapLongClick(p0: LatLng) {
        mMap.clear() // birden fazla marker ekleyince bir öncekinin silinmesi için
        mMap.addMarker(MarkerOptions().position(p0))

        // Add Activity'e yollanacak enlem ve boylam
        selectedLatitude = p0.latitude
        selectedLongitude = p0.longitude

    }

}