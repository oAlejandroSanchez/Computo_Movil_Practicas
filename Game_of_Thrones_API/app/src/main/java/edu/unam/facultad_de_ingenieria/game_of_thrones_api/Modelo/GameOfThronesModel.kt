package edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo

data class GameOfThronesModel(
    val id: Long,
    val nombre_completo: String,
    val titulo: String,
    val familia: String,
    val imagenURL: String
)