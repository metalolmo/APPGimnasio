package es.ua.eps.gimnasioapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

            // PESO
            val pesoLayout = TextInputLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginEnd = 8
                }
                isHintEnabled = false
                boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_FILLED
                boxBackgroundColor = Color.parseColor("#F0FFFFFF") // casi blanco, buena visibilidad
                setBoxCornerRadii(24f, 24f, 24f, 24f)
            }

            val pesoInput = TextInputEditText(this).apply {
                hint = "Peso (kg)"
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                setTextColor(Color.WHITE)               // ← texto blanco
                setHintTextColor(Color.LTGRAY)          // ← hint gris claro
                setBackgroundColor(Color.TRANSPARENT)   // ← evita solapamiento de color base
                setPadding(32, 24, 32, 24)
            }

            pesoLayout.addView(pesoInput)

            // REPS
            val repsLayout = TextInputLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginStart = 8
                }
                isHintEnabled = false
                boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_FILLED
                boxBackgroundColor = Color.parseColor("#F0FFFFFF")
                setBoxCornerRadii(24f, 24f, 24f, 24f)
            }

            val repsInput = TextInputEditText(this).apply {
                hint = "Reps"
                inputType = InputType.TYPE_CLASS_NUMBER
                setTextColor(Color.WHITE)
                setHintTextColor(Color.LTGRAY)
                setBackgroundColor(Color.TRANSPARENT)
                setPadding(32, 24, 32, 24)
            }
            repsLayout.addView(repsInput)

            pesoInput.addTextChangedListener {
                serie.peso = it.toString().toFloatOrNull() ?: 0f
            }

            repsInput.addTextChangedListener {
                serie.repeticiones = it.toString().toIntOrNull() ?: 0
            }

            fila.addView(pesoLayout)
            fila.addView(repsLayout)
            contenedor.addView(fila)
        }
        

        btnGuardar.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("nombre", nombre)
                putExtra("seriesRealizadas", ArrayList(listaSeries))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}