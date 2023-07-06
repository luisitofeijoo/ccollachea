package com.sisnperu.ccollachea.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sisnperu.ccollachea.R
import com.sisnperu.ccollachea.database.entities.PersonaEntity

class PersonaAdapter(private val personas: List<PersonaEntity>) : RecyclerView.Adapter<PersonaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_persona, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val persona = personas[position]
        holder.bind(persona)
    }

    override fun getItemCount(): Int {
        return personas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewFoto: ImageView = itemView.findViewById(R.id.imageViewFoto)
        private val textViewNombres: TextView = itemView.findViewById(R.id.textViewNombres)
        private val textViewDni: TextView = itemView.findViewById(R.id.textViewDni)

        fun bind(persona: PersonaEntity) {
            // Aquí puedes asignar los valores de persona a los elementos de la vista
            textViewNombres.text = persona.nombres
            textViewDni.text = persona.dni
            // Aquí puedes cargar la imagen desde la cadena Base64 en imageViewFoto
            // Puedes utilizar bibliotecas como Glide o Picasso para cargar imágenes desde una cadena Base64
        }
    }
}