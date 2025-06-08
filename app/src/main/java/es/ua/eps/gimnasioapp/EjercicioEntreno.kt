package es.ua.eps.gimnasioapp

import java.io.Serializable

data class EjercicioEntreno(
    val nombre: String,
    val seriesTotales: Int,
    val pesosPorSerie: MutableList<Float> = MutableList(seriesTotales) { 0f }
) : Serializable
