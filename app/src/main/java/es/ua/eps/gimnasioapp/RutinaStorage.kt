package es.ua.eps.gimnasioapp

import android.content.Context
import android.util.Log
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

            // Guardar ejercicios como JSONArray
            val ejerciciosArray = JSONArray()
            for (ejercicio in rutina.listaEjercicios) {
                val ejJson = JSONObject()
                ejJson.put("nombre", ejercicio.nombre)
                ejJson.put("seriesTotales", ejercicio.seriesTotales)

                // Guardar pesos por serie
                val pesosArray = JSONArray()
                ejercicio.pesosPorSerie.forEach { peso ->
                    pesosArray.put(peso)
                }
                ejJson.put("pesosPorSerie", pesosArray)

                ejerciciosArray.put(ejJson)
            }

            json.put("ejercicios", ejerciciosArray)
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
                val duracion = try {
                    obj.getInt("duracion")
                } catch (e: Exception) {
                    val texto = obj.getString("duracion")
                    texto.filter { it.isDigit() }.toIntOrNull() ?: 0
                }

                val listaEjercicios = mutableListOf<EjercicioEntreno>()
                val ejerciciosArray = obj.optJSONArray("ejercicios") ?: JSONArray()

                for (j in 0 until ejerciciosArray.length()) {
                    val ejObj = ejerciciosArray.getJSONObject(j)
                    val nombreEjercicio = ejObj.getString("nombre")
                    val seriesTotales = ejObj.getInt("seriesTotales")

                    val pesosPorSerie = mutableListOf<Float>()
                    val pesosArray = ejObj.optJSONArray("pesosPorSerie") ?: JSONArray()
                    for (k in 0 until pesosArray.length()) {
                        pesosPorSerie.add(pesosArray.getDouble(k).toFloat())
                    }

                    listaEjercicios.add(
                        EjercicioEntreno(nombreEjercicio, seriesTotales, pesosPorSerie)
                    )
                }

                rutinas.add(Rutina(nombre, descripcion, duracion, listaEjercicios))
            }
        } catch (e: Exception) {
            Log.e("RutinaStorage", "Error al cargar rutinas", e)
        }

        return rutinas
    }
}