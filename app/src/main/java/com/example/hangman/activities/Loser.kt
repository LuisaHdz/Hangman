package com.example.hangman.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.example.hangman.R
import com.example.hangman.databinding.ActivityLoserBinding
import com.example.hangman.utils.Constants

class Loser : AppCompatActivity() {

    private lateinit var binding: ActivityLoserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras


        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@Loser, MainActivity::class.java)
                startActivity(intent)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        if (bundle != null) {
            findViewById<TextView>(R.id.tvAnswer).text = bundle.getString("word").toString()

            //Para visualizar en LOGS lo que le llega al Bundle
            Log.d(Constants.LOGTAG, getString(R.string.bundleData, bundle.getString("word")))

        }else{
            Log.d(Constants.LOGTAG, getString(R.string.bundle_null))
        }


        binding.btnEnd.setOnClickListener {
            finishAffinity()
        }

        binding.btnNewGame.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

}