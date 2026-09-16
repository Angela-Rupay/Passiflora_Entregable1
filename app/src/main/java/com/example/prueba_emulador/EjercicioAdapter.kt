package com.example.prueba_emulador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba_emulador.network.modelos.Ejercicio

class EjercicioAdapter(
    private val ejercicios: MutableList<Ejercicio>
) : RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder>() {

    class EjercicioViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titulo: TextView =
            itemView.findViewById(R.id.tv_titulo_ejercicio)

        val descripcion: TextView =
            itemView.findViewById(R.id.tv_descripcion_ejercicio)

        val duracion: TextView =
            itemView.findViewById(R.id.tv_duracion_ejercicio)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjercicioViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_ejercicio,
                parent,
                false
            )

        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EjercicioViewHolder,
        position: Int
    ) {

        val ejercicio = ejercicios[position]

        holder.titulo.text = ejercicio.titulo
        holder.descripcion.text = ejercicio.descripcion

        holder.duracion.text =
            "Duración: ${ejercicio.duracion} minutos"
    }

    override fun getItemCount(): Int {
        return ejercicios.size
    }

    fun agregarEjercicio(ejercicio: Ejercicio) {

        ejercicios.add(ejercicio)

        notifyItemInserted(ejercicios.size - 1)
    }
}