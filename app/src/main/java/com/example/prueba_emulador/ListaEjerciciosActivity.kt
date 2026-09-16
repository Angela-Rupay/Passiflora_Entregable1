package com.example.prueba_emulador

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba_emulador.network.modelos.Ejercicio
import com.example.prueba_emulador.network.modelos.EjercicioRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaEjerciciosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EjercicioAdapter
    private lateinit var progressBar: ProgressBar

    private val ejercicios = mutableListOf<Ejercicio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_ejercicios)

        recyclerView = findViewById(R.id.rv_ejercicios)
        progressBar = findViewById(R.id.progress_ejercicios)

        val btnAgregar =
            findViewById<Button>(R.id.btn_agregar_ejercicio)

        adapter = EjercicioAdapter(ejercicios)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        // Cargar ejercicios desde MockAPI
        cargarEjercicios()

        btnAgregar.setOnClickListener {
            agregarEjercicio()
        }
    }

    private fun cargarEjercicios() {

        progressBar.visibility = View.VISIBLE

        EjercicioRetrofitClient.instance
            .obtenerEjercicios()
            .enqueue(object : Callback<List<Ejercicio>> {

                override fun onResponse(
                    call: Call<List<Ejercicio>>,
                    response: Response<List<Ejercicio>>
                ) {

                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {

                        ejercicios.clear()

                        response.body()?.let {
                            ejercicios.addAll(it)
                        }

                        adapter.notifyDataSetChanged()

                    } else {

                        Toast.makeText(
                            this@ListaEjerciciosActivity,
                            "Error al cargar ejercicios: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<List<Ejercicio>>,
                    t: Throwable
                ) {

                    progressBar.visibility = View.GONE

                    Toast.makeText(
                        this@ListaEjerciciosActivity,
                        "Error de conexión: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun agregarEjercicio() {

        val nuevoEjercicio = Ejercicio(
            titulo = "Respiración 4-7-8",
            descripcion = "Ejercicio de respiración para relajarse",
            duracion = 5
        )

        EjercicioRetrofitClient.instance
            .agregarEjercicio(nuevoEjercicio)
            .enqueue(object : Callback<Ejercicio> {

                override fun onResponse(
                    call: Call<Ejercicio>,
                    response: Response<Ejercicio>
                ) {

                    if (response.isSuccessful) {

                        response.body()?.let {
                            adapter.agregarEjercicio(it)
                        }

                        Toast.makeText(
                            this@ListaEjerciciosActivity,
                            "Ejercicio agregado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                            this@ListaEjerciciosActivity,
                            "Error al agregar: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<Ejercicio>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@ListaEjerciciosActivity,
                        "Error de conexión: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}