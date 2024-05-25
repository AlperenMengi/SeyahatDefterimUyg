package com.alperenmengi.seyehatdefterim.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.alperenmengi.seyehatdefterim.R
import com.alperenmengi.seyehatdefterim.databinding.ActivityAskAndLearnBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.Locale

class AskAndLearnActivity : AppCompatActivity(), TextToSpeech.OnInitListener  {

    private var tts : TextToSpeech? = null
    private val client = OkHttpClient()
    private lateinit var binding : ActivityAskAndLearnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskAndLearnBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //binding.response.text = "Türkiyenin başkenti ankaradır"

        // TextToSpeech(Context: this, OnInitListener: this)
        tts = TextToSpeech(this, this)

        binding.send.setOnClickListener {
            val question = binding.prompt.text.toString()
            Toast.makeText(this, question, Toast.LENGTH_LONG).show()
            getResponse(question){apiResponse ->
                runOnUiThread{
                    binding.scrollView.visibility = View.VISIBLE
                    binding.response.text = apiResponse
                    if (!binding.response.text.equals("")){
                        speakOut()
                    }
                }
            }
        }
    }

    fun getResponse(question: String, callback:(String) -> Unit){
        val key = "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" //alprnmengi@gmail.com
        //val key = "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" // alprnmengi97@gmail.com
        val url = "https://api.openai.com/v1/completions"

        //api
        val requestBody= """
            {
            "prompt": "$question",
            "model": "gpt-3.5-turbo-instruct",
            "max_tokens": 500,
            "temperature": 0
            }
        """.trimIndent()
        //api
        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization","Bearer $key")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        //OKHTTP
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error","API failed",e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body=response.body?.string()
                if (body != null) {
                    Log.v("data",body)
                }
                else{
                    Log.v("data","empty")
                }
                val jsonObject= JSONObject(body)
                val jsonArray: JSONArray =jsonObject.getJSONArray("choices")
                val textResult=jsonArray.getJSONObject(0).getString("text")
                callback(textResult)
            }
        })
    }

    private fun speakOut() {
        val text =  binding.response.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onDestroy() {
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    //TextToSpeech'ten geliyor burası.
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Türkçeyi desteklemesi için
            //sadece bu kısım yeterli değil, ayrıca telefonun ayarlarından textToSpeech kısmının Engine ksımını Google ile değiştirdim.
            val result = tts!!.setLanguage(Locale("tr", "TR"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                println("The Language not supported!")
            } else {
                //binding.speech.isEnabled = true
            }
        }
    }
}




/*
//ONCREATE İÇİNDEYDİ
    prompt.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
    if (actionId == EditorInfo.IME_ACTION_SEND) {

        // setting response tv on below line.
        response.text = "Please wait.."

        // validating text
        val question = prompt.text.toString().trim()
        Toast.makeText(this,question, Toast.LENGTH_SHORT).show()
        if(question.isNotEmpty()){
            getResponse(question) { apiResponse ->
                runOnUiThread {
                    response.text = apiResponse
                }
            }
        }
        return@OnEditorActionListener true
    }
    false
})*/