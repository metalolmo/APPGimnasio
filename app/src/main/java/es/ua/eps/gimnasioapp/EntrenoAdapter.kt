package es.ua.eps.gimnasioapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import es.ua.eps.gimnasioapp.R


class EntrenoAdapter(private val ejercicios: List<EjercicioEntreno>) :
    RecyclerView.Adapter<EntrenoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.tvNombreEjercicio)
        val series = view.findViewById<TextView>(R.id.tvSeries)
        val btnCompletar = view.findViewById<Button>(R.id.btnCompletar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entreno_ejercicio, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        val seriesHechas = ejercicio.pesosPorSerie.count { it > 0f }

        holder.nombre.text = ejercicio.nombre
        holder.series.text = "Series: $seriesHechas/${ejercicio.seriesTotales}"

        holder.btnCompletar.setOnClickListener {
            val index = ejercicio.pesosPorSerie.indexOfFirst { it == 0f }
            if (index != -1) {
                ejercicio.pesosPorSerie[index] = 1f // Simula que ha hecho una serie
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = ejercicios.size
}