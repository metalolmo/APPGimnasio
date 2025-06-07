package es.ua.eps.gimnasioapp

import android.content.Context
import android.util.Log
import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject

object RutinaStorage {
    private const val PREFS_NAME = "rutinas_prefs"
    private const val KEY_RUTINAS = "rutinas"

    fun guardarRutinas(context: Context, rutinas: List<Rutina>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonArray = JSONArray()
        for (rutina in rutinas) {
            val json = JSONObject()
            json.put("nombre", rutina.nombre)
            json.put("descripcion", rutina.descripcion)
            json.put("duracion", rutina.duracion)
            jsonArray.put(json)
        }
        prefs.edit().putString(KEY_RUTINAS, jsonArray.toString()).apply()
    }

    fun cargarRutinas(context: Context): List<Rutina> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_RUTINAS, null) ?: return emptyList()

        val rutinas = mutableListOf<Rutina>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val nombre = obj.getString("nombre")
                val descripcion = obj.getString("descripcion")
                val duracion = obj.getString("duracion")
                rutinas.add(Rutina(nombre, descripcion, duracion))
            }
        } catch (e: Exception) {
            Log.e("RutinaStorage", "Error al cargar rutinas", e)
        }
        return rutinas
    }
}