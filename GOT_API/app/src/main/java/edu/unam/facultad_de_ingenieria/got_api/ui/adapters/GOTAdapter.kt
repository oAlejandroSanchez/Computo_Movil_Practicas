package edu.unam.facultad_de_ingenieria.got_api.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.unam.facultad_de_ingenieria.got_api.databinding.GotPersonajeElementBinding
import edu.unam.facultad_de_ingenieria.got_api.model.GOTModel
import java.lang.reflect.Type

class GOTAdapter(private
    var personajes: ArrayList<GOTModel>,
    private var onGameClicked: (GOTModel) -> Unit
): RecyclerView.Adapter<GOTAdapter.ViewHolder>() {
    class ViewHolder(private var binding: GotPersonajeElementBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(personaje: GOTModel) {
            binding.tvNombre.text = personaje.firstName
            binding.tvApellido.text = personaje.lastName

            Glide.with(itemView.context)
                .load(personaje.imageUrl)
                .into(binding.ivPersonaje)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GotPersonajeElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = personajes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val personaje = personajes[position]

        holder.bind(personaje)

        holder.itemView.setOnClickListener {
            onGameClicked(personaje)
        }
    }
}