package es.ua.eps.gimnasioapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.ua.eps.gimnasioapp.SerieRegistro

class EjercicioHistorialAdapter(
    private val ejercicios: MutableMap<String, List<SerieRegistro>>,
    private val onEliminar: (String) -> Unit
) : RecyclerView.Adapter<EjercicioHistorialAdapter.EjercicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ejercicio_historial, parent, false)
        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val nombre = ejercicios.keys.elementAt(position)
        val series = ejercicios[nombre] ?: emptyList()
        holder.bind(nombre, series)
    }

    override fun getItemCount(): Int = ejercicios.size

    inner class EjercicioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNombre = view.findViewById<TextView>(R.id.tvNombreEjercicio)
        private val tvSeries = view.findViewById<TextView>(R.id.tvSeries)
        private val btnEliminar = view.findViewById<Button>(R.id.btnEliminarEjercicio)

        fun bind(nombre: String, series: List<SerieRegistro>) {
            tvNombre.text = nombre
            tvSeries.text = series.mapIndexed { i, s ->
                "Serie ${i + 1}: ${s.peso} kg x ${s.repeticiones} reps"
            }.joinToString("\n")

            btnEliminar.setOnClickListener {
                onEliminar(nombre)
            }
        }
    }
}