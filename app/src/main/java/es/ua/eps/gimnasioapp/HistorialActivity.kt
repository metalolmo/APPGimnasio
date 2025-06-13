package es.ua.eps.gimnasioapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class HistorialActivity : AppCompatActivity() {

    private lateinit var calendarView: MaterialCalendarView
    val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val entrenamientosMap = mutableMapOf<String, EntrenamientoRealizado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        calendarView = findViewById(R.id.calendarView)

        // Cargar historial
        val historial = HistorialStorage.cargar(this)
        historial.forEach { entrenamientosMap[it.fecha] = it }

        // Crear conjunto de fechas a decorar
        val fechasEntrenadas = historial.mapNotNull {
            try {
                val fecha = LocalDate.parse(it.fecha, formato)
                CalendarDay.from(fecha.year, fecha.monthValue - 1, fecha.dayOfMonth)
            } catch (e: Exception) {
                null
            }
        }.toSet()

        // Aplicar decorador
        calendarView.addDecorator(DiaEntrenadoDecorator(fechasEntrenadas))

        // Escuchar clics en el calendario
        calendarView.setOnDateChangedListener { _, date, _ ->
            val fecha = String.format("%04d-%02d-%02d", date.year, date.month + 1, date.day)
            val entrenamiento = entrenamientosMap[fecha]

            if (entrenamiento != null) {
                mostrarResumenEntrenamiento(entrenamiento)
            } else {
                Toast.makeText(this, "No hay entrenamiento registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun mostrarResumenEntrenamiento(ent: EntrenamientoRealizado) {
        val recyclerView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@HistorialActivity)
            adapter = EjercicioHistorialAdapter(
                ent.ejercicios.toMutableMap(),
                onEliminar = { ejercicioNombre ->
                    ent.ejercicios.remove(ejercicioNombre)
                    val nuevaLista = entrenamientosMap.values.toList()
                    HistorialStorage.guardarTodo(this@HistorialActivity, nuevaLista)
                    Toast.makeText(this@HistorialActivity, "Ejercicio eliminado", Toast.LENGTH_SHORT).show()
                }
            )
            setPadding(40, 20, 40, 20)
        }

        AlertDialog.Builder(this)
            .setTitle("Entrenamiento del ${ent.fecha}")
            .setView(recyclerView)
            .setPositiveButton("Cerrar", null)
            .show()
    }



}