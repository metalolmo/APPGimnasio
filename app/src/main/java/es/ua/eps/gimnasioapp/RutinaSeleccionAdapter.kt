package es.ua.eps.gimnasioapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RutinaSeleccionAdapter(
    private val rutinas: List<Rutina>,
    private val onRutinaSeleccionada: (Rutina) -> Unit
) : RecyclerView.Adapter<RutinaSeleccionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.tvNombreRutina)
        val btnSeleccionar = view.findViewById<Button>(R.id.btnSeleccionarRutina)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rutina_seleccion, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rutina = rutinas[position]
        holder.nombre.text = rutina.nombre
        holder.btnSeleccionar.setOnClickListener {
            onRutinaSeleccionada(rutina)
        }
    }

    override fun getItemCount(): Int = rutinas.size
}