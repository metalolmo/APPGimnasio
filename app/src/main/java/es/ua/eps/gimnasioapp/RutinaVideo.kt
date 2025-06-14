package es.ua.eps.gimnasioapp

data class RutinaVideo(
    val titulo: String,
    val descripcion: String,
    val videoId: String,
    val ejercicios: List<EjercicioDetalle> = emptyList() // Añadido aquí
)
