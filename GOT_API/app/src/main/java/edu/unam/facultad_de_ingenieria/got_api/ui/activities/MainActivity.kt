/*
* Universidad Nacional Autónoma de México
* Facultad de Ingeniería
*
* Autor: Sánchez Pérez Omar Alejandro
* Asignatura: Cómputo Móvil
* Semestre: 2024-1
* */

package edu.unam.facultad_de_ingenieria.got_api.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.unam.facultad_de_ingenieria.got_api.R
import edu.unam.facultad_de_ingenieria.got_api.databinding.MainActivityBinding
import edu.unam.facultad_de_ingenieria.got_api.model.GOTModel
import edu.unam.facultad_de_ingenieria.got_api.network.GOTApi
import edu.unam.facultad_de_ingenieria.got_api.network.RetrofitService
import edu.unam.facultad_de_ingenieria.got_api.ui.adapters.GOTAdapter
import edu.unam.facultad_de_ingenieria.got_api.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        /* Se ejecuta el método principal */
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val call = RetrofitService.getRetrofit()
            .create(GOTApi::class.java)
            .getPersonaje("api/v2/Characters")

        call.enqueue(object: Callback<ArrayList<GOTModel>> {
            override fun onResponse(
                call: Call<ArrayList<GOTModel>>,
                response: Response<ArrayList<GOTModel>>
            ) {
                binding.pbLoader.visibility = View.INVISIBLE

                Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                val personajesAdapter = GOTAdapter(response.body()!!){ personaje ->
                    //Click en cada personaje
                    val bundle = bundleOf(
                        "id" to personaje.id
                    )

                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

                binding.rvPersonajesGOT.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                binding.rvPersonajesGOT.adapter = personajesAdapter

            }

            override fun onFailure(call: Call<ArrayList<GOTModel>>, t: Throwable) {
                binding.pbLoader.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity,
                    R.string.no_network,
                    Toast.LENGTH_SHORT)
                    .show()
            }

        })
            }
}
