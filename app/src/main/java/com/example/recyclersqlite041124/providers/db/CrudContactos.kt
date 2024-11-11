package com.example.recyclersqlite041124.providers.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.recyclersqlite041124.Aplicacion
import com.example.recyclersqlite041124.models.ContactoModel

class CrudContactos {
    fun create(c: ContactoModel): Long {
        val con = Aplicacion.llave.writableDatabase
        return try {
            // Si se intenta insertar un registro que existe se indica que la DB no devuelva un error sino que devuelva -1L
            con.insertWithOnConflict(
                Aplicacion.TABLA,
                null,
                c.toContentValues(),
                SQLiteDatabase.CONFLICT_IGNORE
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            -1L
        } finally {
            con.close()
        }
        // Es como un toString pero mapea el contacto
        //val cv = c.toContentValues()
    }

    fun read(): MutableList<ContactoModel> {
        val lista = mutableListOf<ContactoModel>()
        val con = Aplicacion.llave.readableDatabase
        try {
            val cursor = con.query(
                Aplicacion.TABLA,
                arrayOf("id", "nombre", "apellidos", "email", "imagen"),
                null,
                null,
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                val contacto = ContactoModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                )
                lista.add(contacto)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            con.close()
        }
        return lista
    }

    public fun borrar(id: Int): Boolean {
        val con = Aplicacion.llave.writableDatabase
        val contactoBorrado = con.delete(Aplicacion.TABLA, "id=?", arrayOf(id.toString()))
        con.close()
        return contactoBorrado > 0
    }

    // Función de extensión: funciones que permiten darle funcionalidad extra a una clase sin saber su código
    private fun ContactoModel.toContentValues(): ContentValues {
        return ContentValues().apply {
            put("nombre", nombre)
            put("apellidos", apellidos)
            put("email", email)
            put("imagen", imagen)
        }
    }
}