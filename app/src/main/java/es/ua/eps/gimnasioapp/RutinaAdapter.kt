package es.ua.eps.gimnasioapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import es.ua.eps.gimnasioapp.databinding.ItemRutinaBinding
import es.ua.eps.gimnasioapp.RutinasActivity

data class Rutina(
    val nombre: String,
    val descripcion: String,
    val duracion: String
)

class RutinaAdapter(private val rutinas: List<Rutina>) :
    RecyclerView.Adapter<RutinaAdapter.RutinaViewHolder>() {

    fun obtenerRutinas(): List<Rutina> = rutinas

    inner class RutinaViewHolder(private val binding: ItemRutinaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rutina: Rutina) {
            binding.textNombreRutina.text = rutina.nombre

            // Click normal
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetalleRutinaActivity::class.java)
                intent.putExtra("nombre", rutina.nombre)
                intent.putExtra("descripcion", rutina.descripcion)
                intent.putExtra("duracion", rutina.duracion)
                context.startActivity(intent)
            }

            // Click largo: eliminar rutina
            binding.root.setOnLongClickListener {
                val context = binding.root.context
                AlertDialog.Builder(context)
                    .setTitle("Eliminar rutina")
                    .setMessage("¿Deseas eliminar \"${rutina.nombre}\"?")
                    .setPositiveButton("Sí") { _, _ ->
                        (context as? RutinasActivity)?.eliminarRutina(rutina)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val binding = ItemRutinaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RutinaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        holder.bind(rutinas[position])
    }

    override fun getItemCount(): Int = rutinas.size
}