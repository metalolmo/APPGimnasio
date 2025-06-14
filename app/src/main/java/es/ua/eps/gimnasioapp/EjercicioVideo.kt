package es.ua.eps.gimnasioapp

data class EjercicioVideo(
    val nombre: String,
    val descripcion: String,
    val videoId: String,
    val ejercicios: List<String> // Lista de ejercicios como texto
)