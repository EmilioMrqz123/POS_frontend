package com.example.proyectofinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Asegúrate de importar Glide

class VehiculoAdapter(private val vehiculos: List<Vehiculo>) :
    RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>() {

    class VehiculoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCoche: ImageView = itemView.findViewById(R.id.img_coche)
        val tvModelo: TextView = itemView.findViewById(R.id.tv_vehiculo_modelo)
        val tvDetalles: TextView = itemView.findViewById(R.id.tv_vehiculo_detalles)
        val tvPrecio: TextView = itemView.findViewById(R.id.tv_vehiculo_precio)
        val btnComprar: Button = itemView.findViewById(R.id.btn_comprar_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehiculo, parent, false)
        return VehiculoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) {
        val vehiculo = vehiculos[position]

        holder.tvModelo.text = vehiculo.modelo
        holder.tvDetalles.text = vehiculo.detalles
        holder.tvPrecio.text = vehiculo.precio

        // Lógica de Imagen: Si hay URL usa Glide, si no, deja la imagen por defecto del XML
        if (vehiculo.imagenUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(vehiculo.imagenUrl)
                .placeholder(R.drawable.car_placeholder) // Tu imagen dummy en drawables
                .into(holder.imgCoche)
        }

        // Navegación al hacer clic
        holder.btnComprar.setOnClickListener {
            val intent = Intent(holder.itemView.context, CompraActivity::class.java)
            intent.putExtra("vehiculo_seleccionado", vehiculo)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = vehiculos.size
}