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

                // Guardar series como peso + repeticiones
                val seriesArray = JSONArray()
                ejercicio.series.forEach { serie ->
                    val serieJson = JSONObject()
                    serieJson.put("peso", serie.peso)
                    serieJson.put("repeticiones", serie.repeticiones)
                    seriesArray.put(serieJson)
                }
                ejJson.put("series", seriesArray)

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

                    val series = mutableListOf<SerieRegistro>()
                    val seriesArray = ejObj.optJSONArray("series") ?: JSONArray()
                    for (k in 0 until seriesArray.length()) {
                        val serieObj = seriesArray.getJSONObject(k)
                        val peso = serieObj.optDouble("peso", 0.0).toFloat()
                        val repeticiones = serieObj.optInt("repeticiones", 0)
                        series.add(SerieRegistro(peso, repeticiones))
                    }

                    listaEjercicios.add(
                        EjercicioEntreno(nombreEjercicio, seriesTotales, series)
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