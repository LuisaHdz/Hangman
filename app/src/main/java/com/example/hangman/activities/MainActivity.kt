package com.example.hangman.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hangman.R
import com.example.hangman.databinding.ActivityMainBinding
import com.example.hangman.model.Word
import com.example.hangman.network.RetrofitService
import com.example.hangman.network.WordAPI
import com.example.hangman.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Handler
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var wordToGuess: String
    private lateinit var currentGuess: StringBuilder
    private lateinit var tvAnswer: TextView
    private lateinit var tvHint: TextView
    private lateinit var ivError: ImageView
    private lateinit var etGuess: EditText
    //private var guess: Char = '\u0000'
    private lateinit var guessedLetters : ArrayList <Char>
    private var errorLimit: Int = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para que al principio aparezca desactivado el boton
        binding.btnVerify.isEnabled = false

        binding.etGuess.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                //Validar si hay algo en la caja para habilitar el boton
                binding.btnVerify.isEnabled = validaCampos()
            }
        })

        // Se obtienen los datos de: cm/2024-2/hangman.php
        val call = RetrofitService.getRetrofit()
            .create(WordAPI::class.java)
            .getWord("cm/2024-2/hangman.php")


        call.enqueue(object: Callback<Word> {
            override fun onResponse(
                call: Call<Word>,
                response: Response<Word>
            ) {
                //binding.pbConexion.visibility = View.INVISIBLE

                //SE INICIALIZAN VARIABLES
                val category = response.body()!!.category
                wordToGuess = response.body()!!.word.toString()
                //Log.d(Constants.LOGTAG, "WordToGuess: $wordToGuess")

                //Se intercambian las letras de la palabra por "_"
                currentGuess = StringBuilder("_".repeat(wordToGuess.length))
                //Log.d(Constants.LOGTAG, "CurrentGuess: $currentGuess")

                guessedLetters = ArrayList()
                etGuess = findViewById(R.id.etGuess)
                //guess = findViewById<EditText>(R.id.etGuess).text[0].lowercaseChar()

                findViewById<TextView>(R.id.tvAnswer).text = currentGuess
                //Visualizar en LOGS los datos que se obtienen de la pagina
                Log.d(Constants.LOGTAG, getString(R.string.response, response.body().toString()))

                //Mostrar pista al usuario
                findViewById<TextView>(R.id.tvHint).text = getString(R.string.hintOutput, category)

            }

            override fun onFailure(call: Call<Word>, t: Throwable) {
                //binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity,
                    getString(R.string.no_connection),
                    Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent (this@MainActivity, NoConnection::class.java)
                startActivity(intent)
            }

        } )

        binding.btnVerify.setOnClickListener{
            existeLetra(etGuess.text[0].lowercaseChar())
            //Para que desaparezca la letra cuando se de click
            etGuess.text.clear()

        }

        binding.btnEnd.setOnClickListener {
            finishAffinity()
        }

    }

    fun existeLetra(letter: Char) {
        var cambios = 0
        ivError = findViewById(R.id.ivError)

        if (guessedLetters.contains(letter)) {
            //Si la letra ya se intento anteriormente
            Toast.makeText(
                this@MainActivity,
                getString(R.string.guessedLetters, letter), Toast.LENGTH_SHORT
            ).show()
        } else {
            //Se guarda la letra adivinada para llevar un control
            guessedLetters.add(letter)
            //Se revisa si la letra esta contenida en la palabra a adivinar
            for (i in wordToGuess.indices) {
                if (wordToGuess[i] == letter) {
                    //Si la letra coincide, se llena el espacio
                    currentGuess.setCharAt(i, letter)
                    cambios += 1
                }
            }

            if (cambios == 0) {
                errorLimit -= 1
                if (errorLimit != 0) {

                    Toast.makeText(
                        this@MainActivity, getString(R.string.notFound,letter,errorLimit), Toast.LENGTH_SHORT
                    ).show()

                    when(errorLimit) {
                        5 -> ivError.setImageResource(R.drawable.rightleg)

                        4 -> ivError.setImageResource(R.drawable.leftleg)

                        3 -> ivError.setImageResource(R.drawable.rightarm)

                        2 -> ivError.setImageResource(R.drawable.leftarm)

                        1 -> ivError.setImageResource(R.drawable.body)

                        else -> Toast.makeText(this@MainActivity,
                            getString(R.string.elseWhen), Toast.LENGTH_SHORT).show()
                    }



                } else {
                    //Ya no le quedan oportunidades al usuario
                    //Toast.makeText(this@MainActivity, getString(R.string.lose),Toast.LENGTH_SHORT).show()
                    //Se quita cabeza
                    ivError.setImageResource(R.drawable.head)

                    Handler().postDelayed({
                        val intent = Intent (this, Loser::class.java)
                        val respuesta = Bundle()
                        respuesta.putString("word",wordToGuess)
                        intent.putExtras(respuesta)
                        startActivity(intent)
                    },1100)

                }

            }else{
                //Se muestra la modificacion
                findViewById<TextView>(R.id.tvAnswer).text = currentGuess

                if (currentGuess.toString() == wordToGuess) {
                    //El jugador gana, es decir, adivina todas las letras
                    //Toast.makeText(this@MainActivity, getString(R.string.win), Toast.LENGTH_SHORT).show()
                    val intent = Intent (this, Winner::class.java)
                    startActivity(intent)
                }else{
                    //La letra esta en la palabra
                    Toast.makeText(this@MainActivity, getString(R.string.correctGuess),Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun validaCampos(): Boolean = binding.etGuess.text.isNotEmpty()


    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

}