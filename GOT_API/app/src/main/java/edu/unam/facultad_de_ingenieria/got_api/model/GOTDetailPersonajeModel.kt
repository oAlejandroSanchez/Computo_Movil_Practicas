package edu.unam.facultad_de_ingenieria.got_api.model

import com.google.gson.annotations.SerializedName

data class GOTDetailPersonajeModel(
    @SerializedName("id")
    var id : String?,
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
