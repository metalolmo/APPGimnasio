package es.ua.eps.gimnasioapp

import java.io.Serializable

data class EjercicioEntreno(
    val nombre: String,
    val seriesTotales: Int,
    val series: MutableList<SerieRegistro> = MutableList(seriesTotales) { SerieRegistro() }
) : Serializable