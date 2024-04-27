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


        binding.hotel.setOnClickListener(){
            intent = Intent(this@MainActivity, HotelActivity::class.java)
            startActivity(intent)
        }
        binding.museum.setOnClickListener(){
            intent = Intent(this@MainActivity, MuseumActivity::class.java)
            startActivity(intent)
        }
        binding.travel.setOnClickListener(){
            intent = Intent(this@MainActivity, TravelActivity::class.java)
            startActivity(intent)
        }
        binding.askAndLearn.setOnClickListener(){
            intent = Intent(this@MainActivity, AskAndLearnActivity::class.java)
            startActivity(intent)
        }
        binding.place.setOnClickListener(){
            intent = Intent(this@MainActivity, AIActivity::class.java)
            startActivity(intent)
        }

    }


    fun buttonAnimation(){
        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.hotel.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
        binding.travel.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
        binding.askAndLearn.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
        binding.museum.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
        binding.place.setOnClickListener(){
            it.startAnimation(buttonAnimation)
        }
    }

    /*fun chatgpt_Speechtext(view: View) {
        intent = Intent(this@MainActivity, AskAndLearnActivity::class.java)
        startActivity(intent)
    }
    fun hotels(view: View) {
        intent = Intent(this@MainActivity, HotelActivity::class.java)
        startActivity(intent)
    }
    fun museums(view: View) {
        intent = Intent(this@MainActivity, MuseumActivity::class.java)
        startActivity(intent)
    }
    fun travels(view: View) {
        intent = Intent(this@MainActivity, TravelActivity::class.java)
        startActivity(intent)
    }
    fun whereami(view: View) {
        intent = Intent(this@MainActivity, AIActivity::class.java)
        startActivity(intent)
    }*/

}