/*
* Universidad Nacional Autónoma de México
* Facultad de Ingeniería
*
* Autor: Sánchez Pérez Omar Alejandro
* Asignatura: Cómputo Móvil
* Semestre: 2024-1
* */

package edu.unam.facultad_de_ingenieria.game_of_thrones_api.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.Modelo.GameOfThronesModel
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.databinding.ActivityMainBinding
import edu.unam.facultad_de_ingenieria.game_of_thrones_api.databinding.PersonajeElementBinding

//import edu.unam.facultad_de_ingenieria.game_of_thrones_api.databinding.ActivityMainBinding

class GOTAdapter(
        private var personajes : ArrayList<GameOfThronesModel>,
        private var onPersonajeClicked : (GameOfThronesModel) -> Unit
    ) : RecyclerView.Adapter<GOTAdapter.ViewHolder>() {

    class ViewHolder(private var binding: PersonajeElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(personajeGot : GameOfThronesModel){
            binding.tvNombre.text = personajeGot.firstName
            binding.tvApellido.text = personajeGot.lastName

            Glide.with(itemView.context)
                .load(personajeGot.imageUrl)
                .into(binding.imageURLPersonaje)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PersonajeElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = personajes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val personaje = personajes[position]

        holder.bind(personaje)

        holder.itemView.setOnClickListener {
            onPersonajeClicked(personaje)
        }
    }

}