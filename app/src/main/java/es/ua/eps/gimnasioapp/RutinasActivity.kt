package es.ua.eps.gimnasioapp

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import es.ua.eps.gimnasioapp.databinding.ActivityRutinasBinding
import android.content.Intent
import android.widget.Button
import android.widget.Toast

class RutinasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRutinasBinding
    private val listaRutinas = mutableListOf<Rutina>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRutinasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarRutinas)

        // Cargar rutinas guardadas desde almacenamiento
        listaRutinas.addAll(RutinaStorage.cargarRutinas(this))

        // Si no hay rutinas guardadas, cargar unas de ejemplo
        if (listaRutinas.isEmpty()) {
            listaRutinas.addAll(getDummyRutinas())
        }

        // Configurar RecyclerView
        binding.recyclerRutinas.layoutManager = LinearLayoutManager(this)
        binding.recyclerRutinas.adapter = RutinaAdapter(listaRutinas)

        // Botón para agregar nuevas rutinas
        binding.fabAgregarRutina.setOnClickListener {
            mostrarDialogoAgregarRutina()
        }

        // Botón para ver rutinas predefinidas
        binding.btnRutinasPredefinidas.setOnClickListener {
            val intent = Intent(this, RutinasPredefinidasActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDialogoAgregarRutina() {
        val input = EditText(this)
        input.hint = "Nombre de la rutina"

        AlertDialog.Builder(this)
            .setTitle("Nueva Rutina")
            .setView(input)
            .setPositiveButton("Agregar") { _, _ ->
                val nombre = input.text.toString()
                if (nombre.isNotBlank()) {
                    val nuevaRutina = Rutina(nombre, "", 0, emptyList())
                    listaRutinas.add(nuevaRutina)
                    RutinaStorage.guardarRutinas(this, listaRutinas)
                    binding.recyclerRutinas.adapter = RutinaAdapter(listaRutinas)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun getDummyRutinas(): List<Rutina> {
        return listOf(
            Rutina("Pecho y tríceps", "Ejercicios de press, fondos y aperturas.", 45, emptyList()),
            Rutina("Espalda y bíceps", "Dominadas, remo y curl de bíceps.", 50, emptyList()),
            Rutina("Piernas completas", "Sentadillas, zancadas y prensa.", 60, emptyList()),
            Rutina("Abdomen y cardio", "Planchas, crunch y elíptica.", 40, emptyList())
        )
    }

    fun eliminarRutina(rutina: Rutina) {
        listaRutinas.remove(rutina)
        RutinaStorage.guardarRutinas(this, listaRutinas)
        binding.recyclerRutinas.adapter = RutinaAdapter(listaRutinas)
    }
}
