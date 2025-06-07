package es.ua.eps.gimnasioapp

import android.content.Context
import java.io.*

object EjercicioStorage {
    fun guardarEjercicios(context: Context, nombreRutina: String, ejercicios: List<Ejercicio>) {
        try {
            val file = File(context.filesDir, "ejercicios_$nombreRutina.dat")
            ObjectOutputStream(FileOutputStream(file)).use { it.writeObject(ejercicios) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cargarEjercicios(context: Context, nombreRutina: String): List<Ejercicio> {
        val file = File(context.filesDir, "ejercicios_$nombreRutina.dat")
        if (!file.exists()) return emptyList()

        return try {
            ObjectInputStream(FileInputStream(file)).use {
                @Suppress("UNCHECKED_CAST")
                it.readObject() as List<Ejercicio>
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}