package com.example.hangman.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangman.R
import com.example.hangman.databinding.ActivityLoserBinding
import com.example.hangman.databinding.ActivityWinnerBinding

class Winner : AppCompatActivity() {

    private lateinit var binding: ActivityWinnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnd.setOnClickListener {
            finishAffinity()
        }

        binding.btnNewGame.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
}