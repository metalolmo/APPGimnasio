package es.ua.eps.gimnasioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log

class EntrenamientoActivity : AppCompatActivity() {

    private lateinit var ejercicios: ArrayList<EjercicioEntreno>
    private lateinit var rutinaNombre: String
    private val entrenamientoMap = mutableMapOf<String, List<SerieRegistro>>()
    lateinit var launcher: ActivityResultLauncher<Intent> // público para el adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrenamiento)

        // Inicializar el launcher para recoger datos de RegistroSeriesActivity
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val nombre = result.data!!.getStringExtra("nombre") ?: return@registerForActivityResult
                val series = result.data!!.getSerializableExtra("seriesRealizadas") as? ArrayList<SerieRegistro> ?: return@registerForActivityResult

                entrenamientoMap[nombre] = series
                Toast.makeText(this, "Series guardadas para $nombre", Toast.LENGTH_SHORT).show()
            }
        }

        // Obtener vistas
        val tvRutina = findViewById<TextView>(R.id.tvRutinaSeleccionada)
        val rv = findViewById<RecyclerView>(R.id.rvEjerciciosEntreno)
        val btnGuardar = Button(this).apply {
            text = "Guardar entrenamiento"
        }

        // Añadir botón al layout principal
        val layout = findViewById<LinearLayout>(R.id.rootLayoutEntrenamiento)
        layout.addView(btnGuardar)

        // Recuperar datos de la rutina y ejercicios
        rutinaNombre = intent.getStringExtra("rutinaNombre") ?: "Rutina"
        ejercicios = intent.getSerializableExtra("ejercicios") as? ArrayList<EjercicioEntreno> ?: arrayListOf()

        tvRutina.text = "Entrenando: $rutinaNombre"

        // Configurar RecyclerView con el adapter
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = EntrenoAdapter(ejercicios, this)

        // Botón guardar entrenamiento completo
        btnGuardar.setOnClickListener {
            val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            Log.d("GUARDAR_ENTRENO", entrenamientoMap.toString()) // ⬅️ AÑADE ESTO

            val entrenamiento = EntrenamientoRealizado(
                fecha = fecha,
                rutinaNombre = rutinaNombre,
                ejercicios = entrenamientoMap
            )

            HistorialStorage.guardar(this, entrenamiento)
            Toast.makeText(this, "Entrenamiento guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}