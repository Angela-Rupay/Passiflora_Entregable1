package com.example.prueba_emulador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuPrincipalActivity : AppCompatActivity() {

    private lateinit var tvMensajeResultado: TextView
    private lateinit var tvMensajePersonalizado: TextView
    private lateinit var btnVolverEncuesta: Button
    private lateinit var btnCerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        tvMensajeResultado = findViewById(R.id.tv_mensaje_resultado)
        tvMensajePersonalizado = findViewById(R.id.tv_mensaje_personalizado)
        btnVolverEncuesta = findViewById(R.id.btn_volver_encuesta)
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion)

        // Si no llegan por Intent (p.ej. se entró directo por sesión activa),
        // se usan los últimos datos guardados en SharedPreferences
        val nombreUsuario = intent.getStringExtra("EXTRA_NOMBRE")
            ?: PreferenciasUsuario.obtenerNombreCompleto(this)

        val puntajeTotal = intent.getIntExtra("EXTRA_PUNTAJE", -1).let { extra ->
            if (extra >= 0) extra else PreferenciasUsuario.obtenerPuntaje(this)
        }.coerceAtLeast(0)

        val btnEjerciciosRespiracion = findViewById<Button>(R.id.btn_ejercicios_respiracion)
        val btnEjercicios = findViewById<Button>(R.id.btn_ejercicios)
        tvMensajeResultado.text = "$nombreUsuario, tu nivel de ansiedad evaluado según la escala GAD-7 es $puntajeTotal puntos."

        val mensajeApoyo = when (puntajeTotal) {
            in 0..4 -> "Ansiedad mínima o ausente: Sus resultados indican niveles de ansiedad dentro de parámetros habituales. Es fundamental mantener hábitos de autocuidado y atención continua a su bienestar emocional; la prevención es clave en la salud mental."
            in 5..9 -> "Ansiedad leve: Se identifican síntomas leves de ansiedad. Reconocer tempranamente la presencia de malestar emocional demuestra autoconciencia y constituye un primer paso valioso para evitar que los síntomas escalen."
            in 10..14 -> "Ansiedad moderada: Los indicadores sugieren un nivel moderado de afectación sintomatológica. Le recomendamos encarecidamente la valoración por parte de un profesional de la salud mental que evalúe su caso de manera integral, utilizando esta aplicación como un recurso complementario de apoyo."
            in 15..21 -> "Ansiedad severa: Los resultados reflejan un nivel elevado de sintomatología ansiosa que requiere atención prioritaria. Lo que experimenta es clínicamente significativo y merece un espacio de intervención especializada; le instamos a contactar a un profesional de la salud mental a la brevedad posible."
            else -> "Resultado analizado correctamente."
        }

        tvMensajePersonalizado.text = mensajeApoyo

        // Botón para volver a tomar la encuesta (se mantiene igual que ya tenías)
        btnVolverEncuesta.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java).apply {
                putExtra("EXTRA_NOMBRE", nombreUsuario)
            }
            startActivity(intent)
            finish()
        }

        btnEjerciciosRespiracion.setOnClickListener {
            val intent = Intent(this, EjerciciosRespiracionActivity::class.java)
            intent.putExtra("PUNTAJE_TOTAL", puntajeTotal)
            startActivity(intent)
        }

        // Cerrar sesión: borra los datos guardados y regresa a MainActivity limpiando la pila
        btnCerrarSesion.setOnClickListener {
            PreferenciasUsuario.cerrarSesion(this)

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
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