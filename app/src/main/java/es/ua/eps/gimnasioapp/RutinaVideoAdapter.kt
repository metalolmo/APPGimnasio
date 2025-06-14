package es.ua.eps.gimnasioapp


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
class RutinaVideoAdapter(
    private val context: Context,
    private val listaRutinas: List<RutinaVideo>
) : RecyclerView.Adapter<RutinaVideoAdapter.RutinaViewHolder>() {

    class RutinaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.txtTitulo)
        val descripcion: TextView = view.findViewById(R.id.txtDescripcion)
        val btnVerVideo: Button = view.findViewById(R.id.btnVerVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rutina_video, parent, false)
        return RutinaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        val rutina = listaRutinas[position]
        holder.titulo.text = rutina.titulo
        holder.descripcion.text = rutina.descripcion

        // Convertir lista de ejercicios a texto
        val listaEjercicios = rutina.ejercicios.joinToString("\n") {
            "- ${it.nombre}: ${it.descripcion}"
        }
        holder.itemView.findViewById<TextView>(R.id.txtEjercicios).text = listaEjercicios

        holder.btnVerVideo.setOnClickListener {
            val intent = Intent(context, VideoReproductorActivity::class.java)
            intent.putExtra("VIDEO_ID", rutina.videoId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listaRutinas.size
}