package com.example.prueba_emulador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuPrincipalActivity : AppCompatActivity() {

    private lateinit var tvMensajeResultado: TextView
    private lateinit var tvMensajePersonalizado: TextView
    private lateinit var btnDiarioPensamientos: Button
    private lateinit var btnRespiracionGuiada: Button
    private lateinit var btnVolverEncuesta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        // Vincular vistas
        tvMensajeResultado = findViewById(R.id.tv_mensaje_resultado)
        tvMensajePersonalizado = findViewById(R.id.tv_mensaje_personalizado)
        btnVolverEncuesta = findViewById(R.id.btn_volver_encuesta)
        val btnEjercicios = findViewById<Button>(R.id.btn_ejercicios)

        // Recibir los datos enviados desde FormularioActivity
        val nombreUsuario = intent.getStringExtra("EXTRA_NOMBRE") ?: "Usuario"
        val puntajeTotal = intent.getIntExtra("EXTRA_PUNTAJE", 0)

        val btnEjerciciosRespiracion = findViewById<Button>(R.id.btn_ejercicios_respiracion)

        // 1. Mostrar mensaje con el nombre y el puntaje evaluado según la escala GAD-7[cite: 1]
        tvMensajeResultado.text = "$nombreUsuario, tu nivel de ansiedad evaluado según la escala GAD-7 es $puntajeTotal puntos."

        // 2. Usar una estructura 'when' (switch) para asignar el mensaje personalizado según el rango[cite: 1]
        val mensajeApoyo = when (puntajeTotal) {
                in 0..4 -> "Ansiedad mínima o ausente: Sus resultados indican niveles de ansiedad dentro de parámetros habituales. Es fundamental mantener hábitos de autocuidado y atención continua a su bienestar emocional; la prevención es clave en la salud mental."
                in 5..9 -> "Ansiedad leve: Se identifican síntomas leves de ansiedad. Reconocer tempranamente la presencia de malestar emocional demuestra autoconciencia y constituye un primer paso valioso para evitar que los síntomas escalen."
                in 10..14 -> "Ansiedad moderada: Los indicadores sugieren un nivel moderado de afectación sintomatológica. Le recomendamos encarecidamente la valoración por parte de un profesional de la salud mental que evalúe su caso de manera integral, utilizando esta aplicación como un recurso complementario de apoyo."
                in 15..21 -> "Ansiedad severa: Los resultados reflejan un nivel elevado de sintomatología ansiosa que requiere atención prioritaria. Lo que experimenta es clínicamente significativo y merece un espacio de intervención especializada; le instamos a contactar a un profesional de la salud mental a la brevedad posible."
                else -> "Resultado analizado correctamente."
            }


        tvMensajePersonalizado.text = mensajeApoyo

        // 3. Botones del frontend sin acción en el backend (tal como lo indicaste)[cite: 1]

        // 4. Botón para volver a tomar la encuesta (regresa a FormularioActivity)[cite: 1]
        btnVolverEncuesta.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java).apply {
                putExtra("EXTRA_NOMBRE", nombreUsuario)
            }
            startActivity(intent)
            finish() // Cierra esta activity para limpiar la pila
        }

        btnEjerciciosRespiracion.setOnClickListener {
            val intent = Intent(this, EjerciciosRespiracionActivity::class.java)
            // Reutiliza el mismo puntaje que ya usas para mostrar el nivel en esta pantalla
            intent.putExtra("PUNTAJE_TOTAL", puntajeTotal)
            startActivity(intent)
        }
        btnEjercicios.setOnClickListener {

            val intent = Intent(
                this,
                ListaEjerciciosActivity::class.java
            )

            startActivity(intent)
        }
    }
}