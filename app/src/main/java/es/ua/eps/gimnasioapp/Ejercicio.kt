package es.ua.eps.gimnasioapp

import java.io.Serializable

data class Ejercicio(
    val nombre: String,
    val series: Int,
) : Serializable