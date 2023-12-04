/*
* Universidad Nacional Autónoma de México
* Facultad de Ingeniería
*
* Autor: Sánchez Pérez Omar Alejandro
* Asignatura: Cómputo Móvil
* Semestre: 2024-1
* */

package edu.unam.facultad_de_ingenieria.game_of_thrones_api.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo.GOTPersonajeDetail
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.databinding.DetallesPersonajeBinding
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.network.GOTApi
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: DetallesPersonajeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetallesPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val id = bundle?.getString("id", "0")

        val call = RetrofitService.getRetrofit()
            .create(GOTApi::class.java)
            .getPersonajeDetailApi(id)

        call.enqueue(object : Callback<GOTPersonajeDetail> {
            override fun onResponse(
                call: Call<GOTPersonajeDetail>,
                response: Response<GOTPersonajeDetail>
            ) {

                with(binding) {

                    pbLoader.visibility = View.INVISIBLE

                    tvTitle.text = response.body()?.title

                    Glide.with(this@DetailsActivity)
                        .load(response.body()?.imageUrl)
                        .into(ivPersonaje)

                    //tvLongDesc.text = response.body()?.longDesc
                }

            }

            override fun onFailure(call: Call<GOTPersonajeDetail>, t: Throwable) {
                binding.pbLoader.visibility = View.INVISIBLE
                Toast.makeText(
                    this@DetailsActivity,
                    "No hay conexión disponible",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        })
    }
}
