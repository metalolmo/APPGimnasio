package es.ua.eps.gimnasioapp

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SeleccionarRutinaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_rutina)

        val recyclerView = findViewById<RecyclerView>(R.id.rvRutinasGuardadas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ❗ Cargar las rutinas guardadas del almacenamiento real
        val listaRutinas = RutinaStorage.cargarRutinas(this)

        recyclerView.adapter = RutinaSeleccionAdapter(listaRutinas) { rutinaSeleccionada ->
            // Cargar ejercicios reales asociados a la rutina
            val ejerciciosGuardados = EjercicioStorage.cargarEjercicios(this, rutinaSeleccionada.nombre)

            // Convertir a EjercicioEntreno
            val ejerciciosParaEntreno = ejerciciosGuardados.map {
                EjercicioEntreno(
                    nombre = it.nombre,
                    seriesTotales = it.series,
                    series = MutableList(it.series) { SerieRegistro() }
                )
            }

            // Lanzar EntrenamientoActivity
            val intent = Intent(this, EntrenamientoActivity::class.java)
            intent.putExtra("rutinaNombre", rutinaSeleccionada.nombre)
            intent.putExtra("ejercicios", ArrayList(ejerciciosParaEntreno)) // 👈 SOLUCIÓN AQUÍ
            startActivity(intent)
        }
    }

}