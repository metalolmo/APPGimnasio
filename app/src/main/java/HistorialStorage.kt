package es.ua.eps.gimnasioapp

import android.content.Context
import java.io.*

object HistorialStorage {
    fun guardar(context: Context, entrenamiento: EntrenamientoRealizado) {
        val file = File(context.filesDir, "historial.dat")
        val lista = cargar(context).toMutableList()
        lista.add(entrenamiento)
        ObjectOutputStream(FileOutputStream(file)).use {
            it.writeObject(lista)
        }
    }

    fun cargar(context: Context): List<EntrenamientoRealizado> {
        val file = File(context.filesDir, "historial.dat")
        if (!file.exists()) return emptyList()

        return try {
            ObjectInputStream(FileInputStream(file)).use {
                @Suppress("UNCHECKED_CAST")
                it.readObject() as List<EntrenamientoRealizado>
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}