package edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo

import com.google.gson.annotations.SerializedName

data class GameOfThronesModel(
    @SerializedName("id")
    var id : String?,
    @SerializedName("firstName")
    var firstName : String?,
    @SerializedName("lastName")
    var lastName : String?,
    @SerializedName("imageUrl")
    var imageUrl : String?
)