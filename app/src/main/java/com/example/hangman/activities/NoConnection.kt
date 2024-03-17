package com.example.hangman.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangman.R
import com.example.hangman.databinding.ActivityLoserBinding
import com.example.hangman.databinding.ActivityNoConnectionBinding

class NoConnection : AppCompatActivity() {

    private lateinit var binding: ActivityNoConnectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection)

        binding = ActivityNoConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnd.setOnClickListener {
            finishAffinity()
        }

        binding.btnTryAgain.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}