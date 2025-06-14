package es.ua.eps.gimnasioapp


import android.content.Intent
import android.graphics.Color          // ← nuevo import
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

class EntrenoAdapter(
    private val ejercicios: List<EjercicioEntreno>,
    private val activity: EntrenamientoActivity  // Para acceder al launcher
) : RecyclerView.Adapter<EntrenoAdapter.EjercicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entreno_ejercicio, parent, false)
        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bind(ejercicio)
    }

    override fun getItemCount(): Int = ejercicios.size

    inner class EjercicioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNombre: TextView = view.findViewById(R.id.tvNombreEjercicio)

        fun bind(ejercicio: EjercicioEntreno) {
            tvNombre.text = ejercicio.nombre.toString()
            tvNombre.setTextColor(Color.WHITE)
            tvNombre.setBackgroundColor(Color.parseColor("#333333")) // ← color cambiado

            itemView.setOnClickListener {
                val intent = Intent(activity, RegistroSeriesActivity::class.java).apply {
                    putExtra("nombre", ejercicio.nombre)
                    val numSeries = ejercicio.seriesTotales.toString().toIntOrNull() ?: 0
                    putExtra("series", numSeries)
                }
                activity.launcher.launch(intent)
            }
        }
    }
}