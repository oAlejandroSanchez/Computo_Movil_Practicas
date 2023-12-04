/*
* Universidad Nacional Autónoma de México
* Facultad de Ingeniería
*
* Autor: Sánchez Pérez Omar Alejandro
* Asignatura: Cómputo Móvil
* Semestre: 2024-1
* */

package edu.unam.facultad_de_ingenieria.game_of_thrones_api.network

import edu.unam.facultad_de_ingenieria.game_of_thrones_api.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private var INSTANCE: Retrofit? = null

    fun getRetrofit(): Retrofit = INSTANCE ?: synchronized(this@RetrofitService) {
        val instance = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        INSTANCE = instance

        instance
    }
}