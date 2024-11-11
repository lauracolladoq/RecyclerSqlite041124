package com.example.recyclersqlite041124

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclersqlite041124.adapters.ContactoAdapter
import com.example.recyclersqlite041124.databinding.ActivityMainBinding
import com.example.recyclersqlite041124.models.ContactoModel
import com.example.recyclersqlite041124.providers.db.CrudContactos

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lista = mutableListOf<ContactoModel>()
    private lateinit var adapter: ContactoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
        setRecycler()
        title = "Mi agenda"
    }

    private fun traerRegistros() {
        lista = CrudContactos().read()
        if (lista.size > 0) {
            binding.ivContactos.visibility = View.INVISIBLE
        } else {
            binding.ivContactos.visibility = View.VISIBLE
        }
    }

    private fun setRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        traerRegistros()
        //adapter = ContactoAdapter(lista)
        adapter =
            ContactoAdapter(lista, { position -> borrarContacto(position) }, { c -> update(c) })
        binding.recyclerView.adapter = adapter
    }

    private fun update(c: ContactoModel) {
        val i = Intent(this, AddActivity::class.java).apply {
            putExtra("CONTACTO", c)
        }
        startActivity(i)
    }

    private fun borrarContacto(p: Int) {
        val id = lista[p].id
        // Eliminar de la lista mutable
        lista.removeAt(p)
        // Eliminar de la BD
        // Devuelve true si lo puede borrar y false si no
        if (CrudContactos().borrar(id)) {
            adapter.notifyItemRemoved(p)
        } else {
            Toast.makeText(this, "No se eliminó el registro", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    // Para volver a cargar la vista al añadir un registro
    override fun onRestart() {
        super.onRestart()
        setRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_salir -> {
                finish()
            }

            R.id.item_borrar_todo -> {
                confirmarBorrado()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmarBorrado() {
        val builder = AlertDialog.Builder(this).setTitle("¿Borrar Agenda?").setMessage(
            "¿Borrar todos los contactos?"
        ).setNegativeButton("CANCELAR")
        // Cuando en una funcion lambda espera dos parámetros pero estos son opcionales se pone _
        { dialog, _ ->
            dialog.dismiss()
        }.setPositiveButton("ACEPTAR") { _, _ ->
            CrudContactos().borrarTodo()
            setRecycler()
        }.create().show()
    }
}