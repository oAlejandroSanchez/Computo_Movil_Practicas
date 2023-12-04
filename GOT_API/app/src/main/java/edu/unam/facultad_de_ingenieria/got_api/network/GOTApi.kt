package edu.unam.facultad_de_ingenieria.got_api.network

import edu.unam.facultad_de_ingenieria.got_api.model.GOTDetailPersonajeModel
import edu.unam.facultad_de_ingenieria.got_api.model.GOTModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface GOTApi {
    @GET
    fun getPersonaje(
        @Url url: String?

    ): Call<ArrayList<GOTModel>>

    @GET("api/v2/Characters/{id}")
    fun getPersonajeDetail(
        @Path("id") id: String?
    ): Call<GOTDetailPersonajeModel>
}