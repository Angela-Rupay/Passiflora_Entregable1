package com.example.prueba_emulador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FormularioActivity : AppCompatActivity() {

    private lateinit var tvSaludoUsuario: TextView
    private lateinit var btnEvaluar: Button

    private lateinit var rgP1: RadioGroup
    private lateinit var rgP2: RadioGroup
    private lateinit var rgP3: RadioGroup
    private lateinit var rgP4: RadioGroup
    private lateinit var rgP5: RadioGroup
    private lateinit var rgP6: RadioGroup
    private lateinit var rgP7: RadioGroup

    private var nombreCompletoUsuario: String = "Usuario"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        tvSaludoUsuario = findViewById(R.id.tv_saludo_usuario)
        btnEvaluar = findViewById(R.id.btn_evaluar)

        rgP1 = findViewById(R.id.rg_p1)
        rgP2 = findViewById(R.id.rg_p2)
        rgP3 = findViewById(R.id.rg_p3)
        rgP4 = findViewById(R.id.rg_p4)
        rgP5 = findViewById(R.id.rg_p5)
        rgP6 = findViewById(R.id.rg_p6)
        rgP7 = findViewById(R.id.rg_p7)

        nombreCompletoUsuario = intent.getStringExtra("EXTRA_NOMBRE") ?: "Usuario"
        tvSaludoUsuario.text = "Hola $nombreCompletoUsuario, queremos saber tu nivel de ansiedad actual para personalizar tu experiencia"

        btnEvaluar.isEnabled = false

        val listener = RadioGroup.OnCheckedChangeListener { _, _ ->
            verificarFormularioCompleto()
        }

        rgP1.setOnCheckedChangeListener(listener)
        rgP2.setOnCheckedChangeListener(listener)
        rgP3.setOnCheckedChangeListener(listener)
        rgP4.setOnCheckedChangeListener(listener)
        rgP5.setOnCheckedChangeListener(listener)
        rgP6.setOnCheckedChangeListener(listener)
        rgP7.setOnCheckedChangeListener(listener)

        btnEvaluar.setOnClickListener {
            val puntajeTotal = calcularPuntajeTotal()

            // Guardar el puntaje para poder mostrarlo si luego se entra directo al Home
            PreferenciasUsuario.guardarPuntaje(this, puntajeTotal)

            val intent = Intent(this, MenuPrincipalActivity::class.java).apply {
                putExtra("EXTRA_NOMBRE", nombreCompletoUsuario)
                putExtra("EXTRA_PUNTAJE", puntajeTotal)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun verificarFormularioCompleto() {
        val respondido1 = rgP1.checkedRadioButtonId != -1
        val respondido2 = rgP2.checkedRadioButtonId != -1
        val respondido3 = rgP3.checkedRadioButtonId != -1
        val respondido4 = rgP4.checkedRadioButtonId != -1
        val respondido5 = rgP5.checkedRadioButtonId != -1
        val respondido6 = rgP6.checkedRadioButtonId != -1
        val respondido7 = rgP7.checkedRadioButtonId != -1

        btnEvaluar.isEnabled = respondido1 && respondido2 && respondido3 &&
                respondido4 && respondido5 && respondido6 && respondido7
    }

    private fun calcularPuntajeTotal(): Int {
        val p1 = obtenerValorRadio(rgP1.checkedRadioButtonId, R.id.rb_p1_0, R.id.rb_p1_1, R.id.rb_p1_2, R.id.rb_p1_3)
        val p2 = obtenerValorRadio(rgP2.checkedRadioButtonId, R.id.rb_p2_0, R.id.rb_p2_1, R.id.rb_p2_2, R.id.rb_p2_3)
        val p3 = obtenerValorRadio(rgP3.checkedRadioButtonId, R.id.rb_p3_0, R.id.rb_p3_1, R.id.rb_p3_2, R.id.rb_p3_3)
        val p4 = obtenerValorRadio(rgP4.checkedRadioButtonId, R.id.rb_p4_0, R.id.rb_p4_1, R.id.rb_p4_2, R.id.rb_p4_3)
        val p5 = obtenerValorRadio(rgP5.checkedRadioButtonId, R.id.rb_p5_0, R.id.rb_p5_1, R.id.rb_p5_2, R.id.rb_p5_3)
        val p6 = obtenerValorRadio(rgP6.checkedRadioButtonId, R.id.rb_p6_0, R.id.rb_p6_1, R.id.rb_p6_2, R.id.rb_p6_3)
        val p7 = obtenerValorRadio(rgP7.checkedRadioButtonId, R.id.rb_p7_0, R.id.rb_p7_1, R.id.rb_p7_2, R.id.rb_p7_3)

        return p1 + p2 + p3 + p4 + p5 + p6 + p7
    }

    private fun obtenerValorRadio(idSeleccionado: Int, id0: Int, id1: Int, id2: Int, id3: Int): Int {
        return when (idSeleccionado) {
            id0 -> 0
            id1 -> 1
            id2 -> 2
            id3 -> 3
            else -> 0
        }
    }
}