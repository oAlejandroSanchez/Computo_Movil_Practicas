/*
* Universidad Nacional Autónoma de México
* Facultad de Ingeniería
*
* Autor: Sánchez Pérez Omar Alejandro
* Asignatura: Cómputo Móvil
* Semestre: 2024-1
* */

package edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo

import com.google.gson.annotations.SerializedName

data class GOTPersonajeDetail(
    @SerializedName("id")
    var id : Int?,
    @SerializedName("firstName")
    var firstName : String?,
    @SerializedName("lastName")
    var lastName : String?,
    @SerializedName("title")
    var title : String?,
    @SerializedName("family")
    var family : String?,
    @SerializedName("imageUrl")
    var imageUrl : String?
)