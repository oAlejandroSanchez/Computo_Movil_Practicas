package edu.unam.facultad_de_ingenieria.got_api.model

import com.google.gson.annotations.SerializedName

data class GOTModel(
    @SerializedName("id")
    var id : String?,
    @SerializedName("firstName")
    var firstName : String?,
    @SerializedName("lastName")
    var lastName : String?,
    @SerializedName("imageUrl")
    var imageUrl : String?
)
