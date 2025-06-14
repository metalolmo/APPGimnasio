package es.ua.eps.gimnasioapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class EjercicioVideoAdapter(
    private val context: Context,
    private val listaEjercicios: List<EjercicioVideo>
) : RecyclerView.Adapter<EjercicioVideoAdapter.EjercicioViewHolder>() {

    class EjercicioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.txtNombreEjercicio)
        val descripcion = view.findViewById<TextView>(R.id.txtDescripcionEjercicio)
        val btnVerVideo = view.findViewById<Button>(R.id.btnVerVideoEjercicio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_video, parent, false)
        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val ejercicio = listaEjercicios[position]
        holder.nombre.text = ejercicio.nombre
        holder.descripcion.text = ejercicio.descripcion

        holder.btnVerVideo.setOnClickListener {
            val intent = Intent(context, VideoReproductorActivity::class.java)
            intent.putExtra("VIDEO_ID", ejercicio.videoId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listaEjercicios.size
}