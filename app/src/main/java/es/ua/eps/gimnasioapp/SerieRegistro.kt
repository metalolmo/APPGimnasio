package es.ua.eps.gimnasioapp

import java.io.Serializable

data class SerieRegistro(
    var peso: Float = 0f,
    var repeticiones: Int = 0
) : Serializable
