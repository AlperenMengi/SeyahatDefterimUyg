package com.alperenmengi.seyehatdefterim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buttonAnimation() // anasayfadaki butonları tıklayınca büyütüyor

        openHotel()
        openMuseum()
        openTravel()
        openAskAndLearn()
        openWhereAmI()

    }

    fun buttonAnimation(){
        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.hotelCardView.setOnClickListener(){
            binding.hotel.startAnimation(buttonAnimation)
        }
        binding.museumCardView.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
        binding.travelCardView.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
        binding.askAndLearnCardView.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
        binding.placeCardView.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
    }

    fun openHotel(){
        binding.hotelCardView.setOnClickListener(){
            intent = Intent(this@MainActivity, HotelActivity::class.java)
            startActivity(intent)
        }
        binding.hotel.setOnClickListener(){
            intent = Intent(this@MainActivity, HotelActivity::class.java)
            startActivity(intent)
        }
    }
    fun openMuseum(){
        binding.museumCardView.setOnClickListener(){
            intent = Intent(this@MainActivity, MuseumActivity::class.java)
            startActivity(intent)
        }
        binding.museum.setOnClickListener(){
            intent = Intent(this@MainActivity, MuseumActivity::class.java)
            startActivity(intent)
        }
    }
    fun openTravel(){
        binding.travelCardView.setOnClickListener(){
            intent = Intent(this@MainActivity, TravelActivity::class.java)
            startActivity(intent)
        }
        binding.travel.setOnClickListener(){
            intent = Intent(this@MainActivity, TravelActivity::class.java)
            startActivity(intent)
        }
    }
    fun openAskAndLearn(){
        binding.askAndLearnCardView.setOnClickListener(){
            intent = Intent(this@MainActivity, AskAndLearnActivity::class.java)
            startActivity(intent)
        }
        binding.askAndLearn.setOnClickListener(){
            intent = Intent(this@MainActivity, AskAndLearnActivity::class.java)
            startActivity(intent)
        }
    }
    fun openWhereAmI(){
        binding.placeCardView.setOnClickListener(){
            intent = Intent(this@MainActivity, FindLandmarksWithAIActivity::class.java)
            startActivity(intent)
        }
        binding.place.setOnClickListener(){
            intent = Intent(this@MainActivity, FindLandmarksWithAIActivity::class.java)
            startActivity(intent)
        }
    }
}