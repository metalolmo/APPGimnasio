package es.ua.eps.gimnasioapp

import android.app.Activity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.io.Serializable
import android.content.Intent

class RegistroSeriesActivity : AppCompatActivity() {

    private val listaSeries = mutableListOf<SerieRegistro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_series)

        val nombre = intent.getStringExtra("nombre") ?: "Ejercicio"
        val numSeries = intent.getIntExtra("series", 0)

        val tvNombre = findViewById<TextView>(R.id.tvNombreEjercicio)
        val contenedor = findViewById<LinearLayout>(R.id.contenedorSeriesRegistro)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarSeries)

        tvNombre.text = nombre

        for (i in 1..numSeries) {
            val serie = SerieRegistro()
            listaSeries.add(serie)

            val fila = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 12, 0, 12)
                }
            }

            val etPeso = EditText(this).apply {
                hint = "Peso (kg)"
                inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val etReps = EditText(this).apply {
                hint = "Reps"
                inputType = android.text.InputType.TYPE_CLASS_NUMBER
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            etPeso.addTextChangedListener {
                serie.peso = it.toString().toFloatOrNull() ?: 0f
            }

            etReps.addTextChangedListener {
                serie.repeticiones = it.toString().toIntOrNull() ?: 0
            }

            fila.addView(etPeso)
            fila.addView(etReps)
            contenedor.addView(fila)
        }

        btnGuardar.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("nombre", nombre)
            resultIntent.putExtra("seriesRealizadas", ArrayList(listaSeries))
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}