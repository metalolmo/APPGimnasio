package es.ua.eps.gimnasioapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EntrenamientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrenamiento)

        val tvRutina = findViewById<TextView>(R.id.tvRutinaSeleccionada)
        val rv = findViewById<RecyclerView>(R.id.rvEjerciciosEntreno)

        // Recuperar datos enviados
        val rutinaNombre = intent.getStringExtra("rutinaNombre") ?: "Rutina"
        val ejercicios = intent.getSerializableExtra("ejercicios") as? ArrayList<EjercicioEntreno> ?: arrayListOf()

        tvRutina.text = "Entrenando: $rutinaNombre"

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = EntrenoAdapter(ejercicios)
    }
}