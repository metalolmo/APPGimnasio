package es.ua.eps.gimnasioapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object HistorialStorage {
    private const val FILENAME = "historial_entrenamientos.json"

    fun guardar(context: Context, entrenamiento: EntrenamientoRealizado) {
        val lista = cargar(context).toMutableList()
        lista.removeAll { it.fecha == entrenamiento.fecha }
        lista.add(entrenamiento)
        guardarTodo(context, lista)
    }

    fun guardarTodo(context: Context, lista: List<EntrenamientoRealizado>) {
        val json = Gson().toJson(lista)
        File(context.filesDir, FILENAME).writeText(json)
    }

    fun cargar(context: Context): List<EntrenamientoRealizado> {
        val file = File(context.filesDir, FILENAME)
        if (!file.exists()) return emptyList()
        val json = file.readText()
        val type = object : TypeToken<List<EntrenamientoRealizado>>() {}.type
        return Gson().fromJson(json, type)
    }
}