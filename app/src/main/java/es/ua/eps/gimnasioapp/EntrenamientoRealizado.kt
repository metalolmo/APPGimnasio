package es.ua.eps.gimnasioapp

import java.io.Serializable

data class EntrenamientoRealizado(
    val fecha: String,
    val rutinaNombre: String,
    val ejercicios: MutableMap<String, List<SerieRegistro>>
) : Serializable
