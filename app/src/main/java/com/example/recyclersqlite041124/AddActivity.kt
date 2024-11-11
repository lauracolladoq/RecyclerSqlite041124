package com.example.recyclersqlite041124

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclersqlite041124.databinding.ActivityAddBinding
import com.example.recyclersqlite041124.models.ContactoModel
import com.example.recyclersqlite041124.providers.db.CrudContactos

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var nombre = ""
    private var email = ""
    private var apellidos = ""
    private var imagen = ""
    private var id = -1

    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recogerContactos()
        setListeners()

        if (isUpdate) {
            binding.etTitle2.text = "Editar Contacto"
            binding.btn2Enviar.text = "EDITAR"
        }
    }

    private fun recogerContactos() {
        val datos = intent.extras
        if (datos != null) {
            val c = datos.getSerializable("CONTACTO") as ContactoModel
            isUpdate = true
            nombre = c.nombre
            apellidos = c.apellidos
            imagen = c.imagen
            id = c.id
            email = c.email
            pintarDatos()
        }
    }

    private fun pintarDatos() {
        binding.etNombre.setText(nombre)
        binding.etApellidos.setText(apellidos)
        binding.et2Email.setText(email)
    }

    private fun setListeners() {
        binding.btnCancelar.setOnClickListener {
            finish()
        }
        binding.btn2Reset.setOnClickListener {
            limpiar()
        }
        binding.btn2Enviar.setOnClickListener {
            guardarRegistro()
        }
    }

    private fun limpiar() {
        binding.etNombre.setText("")
        binding.etApellidos.setText("")
        binding.et2Email.setText("")
    }

    private fun guardarRegistro() {
        if (datosCorrectos()) {
            imagen = "https://dummyimage.com/200x200/000000/fff&text=" + (nombre.substring(
                0,
                1
            ) + apellidos.substring(0, 2)).uppercase()
            Log.d("INFOimagen-------------------------------------", imagen)
            val c = ContactoModel(id, nombre, apellidos, email, imagen)
            if (!isUpdate) {
                if (CrudContactos().create(c) != -1L) {
                    Toast.makeText(
                        this,
                        "Se ha añadido un registro a la agenda",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                } else {
                    binding.et2Email.error = "ERROR: Email duplicado"
                }
            } else {
                if (CrudContactos().update(c)) {
                    Toast.makeText(this, "Registro editado correctamente", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this, "ERROR: Email duplicado", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun datosCorrectos(): Boolean {
        nombre = binding.etNombre.text.toString().trim()
        apellidos = binding.etApellidos.text.toString().trim()
        email = binding.et2Email.text.toString().trim()

        if (nombre.length < 3) {
            binding.etNombre.error = "El campo nombre debe tener al menos 3 caracteres"
            return false
        }
        if (apellidos.length < 5) {
            binding.etApellidos.error = "El campo apellidos debe tener al menos 5 caracteres"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.et2Email.error = "El campo email debe tener el formato correcto"
            return false
        }
        return true
    }
}






















