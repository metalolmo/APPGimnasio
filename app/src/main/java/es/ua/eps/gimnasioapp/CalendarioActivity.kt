package es.ua.eps.gimnasioapp

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalendarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val fecha = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            Toast.makeText(this, "Fecha seleccionada: $fecha", Toast.LENGTH_SHORT).show()
        }
    }
}