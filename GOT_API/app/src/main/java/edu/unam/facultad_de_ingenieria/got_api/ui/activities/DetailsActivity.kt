package edu.unam.facultad_de_ingenieria.got_api.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.unam.facultad_de_ingenieria.got_api.databinding.GotPersonajeDetailsBinding
import edu.unam.facultad_de_ingenieria.got_api.model.GOTDetailPersonajeModel
import edu.unam.facultad_de_ingenieria.got_api.network.GOTApi
import edu.unam.facultad_de_ingenieria.got_api.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: GotPersonajeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GotPersonajeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val id = bundle?.getString("id", "0")

        val call = RetrofitService.getRetrofit()
            .create(GOTApi::class.java)
            .getPersonajeDetail(id)

        call.enqueue(object: Callback<GOTDetailPersonajeModel> {
            override fun onResponse(call: Call<GOTDetailPersonajeModel>, response: Response<GOTDetailPersonajeModel>) {

                with(binding) {
                    pbLoader.visibility = View.INVISIBLE

                    val title = response.body()?.title
                    if (title.equals("No One", ignoreCase = true)) {
                        tvTitle.text = "Sin información"
                    } else {
                        tvTitle.text = response.body()?.title
                    }

                    Glide.with(this@DetailsActivity)
                        .load(response.body()?.imageUrl)
                        .into(ivImagen)

                    tvNombreCompleto.text = response.body()?.firstName + " " + response.body()?.lastName
                    tvFamilia.text = response.body()?.family?.replace("House ", "")
                    val familyDrawableName = response.body()?.family?.toLowerCase()?.replace("house ", "")

                    if (!familyDrawableName.isNullOrBlank()) {
                        val resourceId = resources.getIdentifier(familyDrawableName, "drawable", packageName)

                        Glide.with(this@DetailsActivity)
                            .load(resourceId)
                            .into(imagenFamilia)
                    }
                }
            }

            override fun onFailure(call: Call<GOTDetailPersonajeModel>, t: Throwable) {
                binding.pbLoader.visibility = View.INVISIBLE
                Toast.makeText(this@DetailsActivity,
                    "No hay conexión disponible",
                    Toast.LENGTH_SHORT)
                    .show()
            }

        })


    }
}