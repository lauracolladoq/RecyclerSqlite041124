package com.example.recyclersqlite041124.adapters

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclersqlite041124.databinding.ContactoLayoutBinding
import com.example.recyclersqlite041124.models.ContactoModel
import com.squareup.picasso.Picasso

class ContactoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val binding = ContactoLayoutBinding.bind(v)

    fun render(
        c: ContactoModel,
        borrarContacto: (Int) -> Unit,
        updateContacto: (ContactoModel) -> Unit
    ) {
        binding.tvNombre.text = c.nombre + " (${c.id})"
        binding.tvEmail.text = c.email
        binding.tvApellidos.text = c.apellidos
        Picasso.get().load(c.imagen).into(binding.imageView)

        binding.btnBorrar.setOnClickListener {
            borrarContacto(adapterPosition)
        }

        binding.btnUpdate.setOnClickListener {
            updateContacto(c)
        }
    }
}