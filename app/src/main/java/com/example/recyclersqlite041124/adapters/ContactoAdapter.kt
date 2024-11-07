package com.example.recyclersqlite041124.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclersqlite041124.R
import com.example.recyclersqlite041124.models.ContactoModel

class ContactoAdapter(
    var lista: MutableList<ContactoModel>
) : RecyclerView.Adapter<ContactoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contacto_layout, parent, false)
        return ContactoViewHolder(v)
    }
    
    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        holder.render(lista[position])
    }
}

