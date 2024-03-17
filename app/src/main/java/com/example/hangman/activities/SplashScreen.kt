package com.example.hangman.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.hangman.R
import kotlin.concurrent.thread

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        thread{

            val versionName = packageManager.getPackageInfo(packageName, 0).versionName
            findViewById<TextView?>(R.id.tvVersion).text = getString(R.string.versionOutput, versionName)

            val creator = this.packageName
            findViewById<TextView?>(R.id.tvCreator).text = getString(R.string.creatorOutput,creator)

            // Esperar un segundo
            Thread.sleep(1000) // Espera 1 segundo (1000 milisegundos)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}