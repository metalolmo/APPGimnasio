package es.ua.eps.gimnasioapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.ua.eps.gimnasioapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Botones de navegación
        binding.btnRutinas.setOnClickListener {
            startActivity(Intent(this, RutinasActivity::class.java))
        }

        binding.btnEntrenar.setOnClickListener {
            startActivity(Intent(this, EntrenamientoActivity::class.java))
        }

        binding.btnHistorial.setOnClickListener {
            startActivity(Intent(this, HistorialActivity::class.java))
        }

        binding.btnEstadisticas.setOnClickListener {
            startActivity(Intent(this, EstadisticasActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_calendario -> {
                startActivity(Intent(this, CalendarioActivity::class.java))
                true
            }
            R.id.menu_acerca_de -> {
                // Aquí podrías mostrar un diálogo más adelante
                true
            }
            R.id.menu_salir -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}



