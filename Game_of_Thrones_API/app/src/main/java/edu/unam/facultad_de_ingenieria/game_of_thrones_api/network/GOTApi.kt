/*
* Universidad Nacional Autónoma de México
* Facultad de Ingeniería
*
* Autor: Sánchez Pérez Omar Alejandro
* Asignatura: Cómputo Móvil
* Semestre: 2024-1
* */

package edu.unam.facultad_de_ingenieria.game_of_thrones_api.network

import edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo.GOTPersonajeDetail
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo.GameOfThronesModel
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.Call
import retrofit2.http.Path

interface GOTApi {
    @GET
    fun getPersonajes(
        @Url url: String?
    ): Call<ArrayList<GameOfThronesModel>>

    @GET("/api/v2/Characters/{id}")
    fun getPersonajeDetailApi(
        @Path("id") id: String?
    ): Call<GOTPersonajeDetail>
}