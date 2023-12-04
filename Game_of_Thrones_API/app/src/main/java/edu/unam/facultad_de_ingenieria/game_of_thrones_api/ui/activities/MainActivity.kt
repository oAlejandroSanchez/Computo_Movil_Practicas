/*
* Universidad Nacional Autónoma de México
* Facultad de Ingeniería
*
* Autor: Sánchez Pérez Omar Alejandro
* Asignatura: Cómputo Móvil
* Semestre: 2024-1
* */

package edu.unam.facultad_de_ingenieria.game_of_thrones_api.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.Animation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.databinding.ActivityMainBinding
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo.GameOfThronesModel
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.R
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.network.GOTApi
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.network.RetrofitService
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.ui.adapters.GOTAdapter
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var objectAnimator: ObjectAnimator
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = RetrofitService.getRetrofit()
            .create(GOTApi::class.java)
            .getPersonajes("api/v2/Characters")
        //.getGames("games/games_list")

        call.enqueue(object: Callback<ArrayList<GameOfThronesModel>> {
            override fun onResponse(
                call: Call<ArrayList<GameOfThronesModel>>,
                response: Response<ArrayList<GameOfThronesModel>>
            ) {
                binding.pbLoader.visibility = View.INVISIBLE

                Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                val gamesAdapter = GOTAdapter(response.body()!!){ personaje ->
                    //Click en cada juego
                    //Toast.makeText(this@MainActivity, "Click en el juego: ${game.title}", Toast.LENGTH_SHORT).show()

                    val bundle = bundleOf(
                        "id" to personaje.id
                    )

                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)

                    intent.putExtras(bundle)

                    startActivity(intent)
                }

                binding.rvPersonajesGOT.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)

                binding.rvPersonajesGOT.adapter = gamesAdapter

            }

            override fun onFailure(call: Call<ArrayList<GameOfThronesModel>>, t: Throwable) {
                binding.pbLoader.visibility = View.INVISIBLE
                /*Toast.makeText(this@MainActivity,
                    "No hay conexión disponible"+Constants.BASE_URL,
                    Toast.LENGTH_SHORT)
                    .show()*/
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(title)
                    .setMessage(Constants.BASE_URL)
                    .setPositiveButton(getString(
                        R.string.btnReintentar)) {
                            dialog, _ -> dialog.dismiss()
                    }.setNegativeButton(getString(R.string.btnSalir)) { dialog, _ ->
                        // Código para manejar el botón "Cancelar"
                        dialog.dismiss()
                        finish()
                    }.show()
            }

        })
    }

    /*fun mensajeUsuario(title:String, msg:String) {
        AlertDialog.Builder(this@MainActivity)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton(getString(
            R.string.btnReintentar)) {
                dialog, _ -> dialog.dismiss()
            }.setNegativeButton(getString(R.string.btnSalir)) { dialog, _ ->
                // Código para manejar el botón "Cancelar"
                dialog.dismiss()
                finish()
            }.show()
    }*/

}