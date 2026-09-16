package com.example.prueba_emulador.network.modelos

import com.example.prueba_emulador.network.EjercicioApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EjercicioRetrofitClient {

    private const val BASE_URL =
        "https://6aab1433ff4dd5698b4f3b52.mockapi.io/"

    val instance: EjercicioApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EjercicioApiService::class.java)
    }
}