package com.example.prueba_emulador.network

import com.example.prueba_emulador.network.modelos.Ejercicio
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EjercicioApiService {

    @GET("ejercicios")
    fun obtenerEjercicios(): Call<List<Ejercicio>>

    @POST("ejercicios")
    fun agregarEjercicio(
        @Body ejercicio: Ejercicio
    ): Call<Ejercicio>
}