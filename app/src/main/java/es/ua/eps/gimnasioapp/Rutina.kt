package es.ua.eps.gimnasioapp

import java.io.Serializable

data class Rutina(
    val nombre: String,
    val descripcion: String,
    val duracion: Int,
    val listaEjercicios: List<EjercicioEntreno>
) : Serializable
