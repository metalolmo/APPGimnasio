package es.ua.eps.gimnasioapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntrenoAdapter(
    private val ejercicios: List<EjercicioEntreno>,
    private val activity: EntrenamientoActivity // Para acceder al launcher
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
        private val tvNombre = view.findViewById<TextView>(R.id.tvNombreEjercicio)

        fun bind(ejercicio: EjercicioEntreno) {
            tvNombre.text = ejercicio.nombre

            // Al hacer clic en el nombre del ejercicio, lanzamos RegistroSeriesActivity
            itemView.setOnClickListener {
                val intent = Intent(activity, RegistroSeriesActivity::class.java)
                intent.putExtra("nombre", ejercicio.nombre)

                val numSeries = ejercicio.seriesTotales.toString().toIntOrNull() ?: 0
                intent.putExtra("series", numSeries)

                activity.launcher.launch(intent)
            }
        }
    }
}