package es.ua.eps.gimnasioapp

import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DetalleRutinaActivity : AppCompatActivity() {
    private lateinit var layoutEjercicios: LinearLayout
    private val ejercicios = mutableListOf<Ejercicio>()
    private lateinit var rutinaNombre: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_rutina)

        val tvNombre = findViewById<TextView>(R.id.tvNombreRutina)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)
        val tvDuracion = findViewById<TextView>(R.id.tvDuracion)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnAgregarEjercicio = findViewById<Button>(R.id.btnAgregarEjercicio)
        layoutEjercicios = findViewById(R.id.layoutEjercicios)

        rutinaNombre = intent.getStringExtra("nombre") ?: "Rutina"
        val descripcion = intent.getStringExtra("descripcion")
        val duracion = intent.getStringExtra("duracion")

        tvNombre.text = rutinaNombre
        tvDescripcion.text = descripcion
        tvDuracion.text = "Duración: $duracion"

        ejercicios.addAll(EjercicioStorage.cargarEjercicios(this, rutinaNombre))
        ejercicios.forEach { agregarEjercicioVista(it) }

        btnEditar.setOnClickListener {
            mostrarDialogoEditarRutina(tvNombre, tvDescripcion, tvDuracion)
        }

        btnAgregarEjercicio.setOnClickListener {
            mostrarDialogoAgregarEjercicio()
        }
    }

    private fun mostrarDialogoEditarRutina(
        tvNombre: TextView,
        tvDescripcion: TextView,
        tvDuracion: TextView
    ) {
        val editNombre = EditText(this).apply { setText(tvNombre.text) }
        val editDescripcion = EditText(this).apply { setText(tvDescripcion.text) }
        val editDuracion = EditText(this).apply { setText(tvDuracion.text.removePrefix("Duración: ")) }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(editNombre)
            addView(editDescripcion)
            addView(editDuracion)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar Rutina")
            .setView(layout)
            .setPositiveButton("Guardar") { _, _ ->
                tvNombre.text = editNombre.text
                tvDescripcion.text = editDescripcion.text
                tvDuracion.text = "Duración: ${editDuracion.text}"
                // Aquí puedes actualizar RutinaStorage si quieres guardar esos datos también
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogoAgregarEjercicio() {
        val inputNombre = EditText(this).apply { hint = "Nombre del ejercicio" }
        val inputSeries = EditText(this).apply { hint = "Nº de series" }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(inputNombre)
            addView(inputSeries)
        }

        AlertDialog.Builder(this)
            .setTitle("Añadir ejercicio")
            .setView(layout)
            .setPositiveButton("Agregar") { _, _ ->
                val nombre = inputNombre.text.toString()
                val series = inputSeries.text.toString()
                if (nombre.isNotBlank() && series.isNotBlank()) {
                    val ejercicio = Ejercicio(nombre, series)
                    ejercicios.add(ejercicio)
                    EjercicioStorage.guardarEjercicios(this, rutinaNombre, ejercicios)
                    agregarEjercicioVista(ejercicio)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun agregarEjercicioVista(ejercicio: Ejercicio) {
        val ejercicioLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 8, 0, 8)
        }

        val texto = TextView(this).apply {
            text = "${ejercicio.nombre} - ${ejercicio.series} series"
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val btnEditar = Button(this).apply {
            text = "✏️"
            setOnClickListener {
                mostrarDialogoEditarEjercicio(ejercicio, texto)
            }
        }

        val btnEliminar = Button(this).apply {
            text = "🗑️"
            setOnClickListener {
                ejercicios.remove(ejercicio)
                EjercicioStorage.guardarEjercicios(this@DetalleRutinaActivity, rutinaNombre, ejercicios)
                layoutEjercicios.removeView(ejercicioLayout)
            }
        }

        ejercicioLayout.addView(texto)
        ejercicioLayout.addView(btnEditar)
        ejercicioLayout.addView(btnEliminar)
        layoutEjercicios.addView(ejercicioLayout)
    }

    private fun mostrarDialogoEditarEjercicio(ejercicio: Ejercicio, textoView: TextView) {
        val inputNombre = EditText(this).apply { setText(ejercicio.nombre) }
        val inputSeries = EditText(this).apply { setText(ejercicio.series) }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(inputNombre)
            addView(inputSeries)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar ejercicio")
            .setView(layout)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevo = Ejercicio(inputNombre.text.toString(), inputSeries.text.toString())
                val index = ejercicios.indexOf(ejercicio)
                if (index != -1) {
                    ejercicios[index] = nuevo
                    textoView.text = "${nuevo.nombre} - ${nuevo.series} series"
                    EjercicioStorage.guardarEjercicios(this, rutinaNombre, ejercicios)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}

