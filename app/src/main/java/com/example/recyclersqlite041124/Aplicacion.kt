package com.example.recyclersqlite041124

import android.app.Application
import android.content.Context
import com.example.recyclersqlite041124.providers.db.MyDatabase

class Aplicacion : Application() {
    companion object {
        const val VERSION = 1
        const val DB = "Base_1"
        const val TABLA = "contactos"
        lateinit var contexto: Context
        lateinit var llave: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        contexto = applicationContext
        llave= MyDatabase()
    }
}