package com.example.recyclersqlite041124.models

import java.io.Serializable

data class ContactoModel(
    val id: Int,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val imagen: String
) : Serializable