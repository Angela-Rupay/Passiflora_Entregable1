package com.example.prueba_emulador

import android.content.Context

object PreferenciasUsuario {

    private const val PREFS_NAME = "pasiflora_prefs"
    private const val KEY_NOMBRE = "nombre"
    private const val KEY_APELLIDO = "apellido"
    private const val KEY_CORREO = "correo"
    private const val KEY_SESION_ACTIVA = "sesion_activa"
    private const val KEY_PUNTAJE = "puntaje_gad7"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun guardarDatos(context: Context, nombre: String, apellido: String, correo: String) {
        prefs(context).edit()
            .putString(KEY_NOMBRE, nombre)
            .putString(KEY_APELLIDO, apellido)
            .putString(KEY_CORREO, correo)
            .putBoolean(KEY_SESION_ACTIVA, true)
            .apply()
    }

    fun obtenerNombre(context: Context): String = prefs(context).getString(KEY_NOMBRE, "") ?: ""
    fun obtenerApellido(context: Context): String = prefs(context).getString(KEY_APELLIDO, "") ?: ""
    fun obtenerCorreo(context: Context): String = prefs(context).getString(KEY_CORREO, "") ?: ""

    fun obtenerNombreCompleto(context: Context): String {
        val nombreCompleto = "${obtenerNombre(context)} ${obtenerApellido(context)}".trim()
        return nombreCompleto.ifEmpty { "Usuario" }
    }

    fun guardarPuntaje(context: Context, puntaje: Int) {
        prefs(context).edit().putInt(KEY_PUNTAJE, puntaje).apply()
    }

    // -1 significa "todavía no ha hecho la encuesta"
    fun obtenerPuntaje(context: Context): Int = prefs(context).getInt(KEY_PUNTAJE, -1)

    fun haySesionActiva(context: Context): Boolean =
        prefs(context).getBoolean(KEY_SESION_ACTIVA, false)

    fun cerrarSesion(context: Context) {
        prefs(context).edit().clear().apply()
    }
}