package com.example.prueba_emulador

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.regex.Pattern
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator

class MainActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var cbTerminos: CheckBox
    private lateinit var btnRegistro: Button

    private lateinit var ivLogo: ImageView

    private lateinit var tvLogo: TextView
    private lateinit var tvBienvenida: TextView
    private lateinit var tvReqLargo: TextView
    private lateinit var tvReqMayus: TextView
    private lateinit var tvReqNum: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular componentes respetando la nomenclatura (inicial_funcion)
        etNombre = findViewById(R.id.et_nombre)
        etApellido = findViewById(R.id.et_apellido)
        etCorreo = findViewById(R.id.et_correo)
        etContrasena = findViewById(R.id.et_contrasena)
        cbTerminos = findViewById(R.id.cb_terminos)
        btnRegistro = findViewById(R.id.btn_registro)
        ivLogo = findViewById(R.id.iv_logo)
        tvLogo = findViewById(R.id.tv_logo)
        tvBienvenida = findViewById(R.id.tv_bienvenida)
        tvReqLargo = findViewById(R.id.tv_req_largo)
        tvReqMayus = findViewById(R.id.tv_req_mayus)
        tvReqNum = findViewById(R.id.tv_req_num)

        // Ejecutar animación nativa inicial al abrir la Activity
        iniciarAnimacion()

        // Configurar validaciones en tiempo real
        configurarValidaciones()

        // Acción del botón de registro
        btnRegistro.setOnClickListener {
            val nombreInput = etNombre.text.toString().trim()
            val apellidoInput = etApellido.text.toString().trim()

            // Validar campos de texto vacíos o con números en nombres
            if (!validarNombresYApellidos()) {
                return@setOnClickListener
            }

            // Validar formato de correo con RegEx
            if (!validarCorreoElectronico()) {
                return@setOnClickListener
            }

            // Unir nombre y apellido manejando valores por defecto con ?: si llegaran a estar vacíos
            val nombreFinal = if (nombreInput.isNotEmpty()) nombreInput else "Usuario"
            val apellidoFinal = if (apellidoInput.isNotEmpty()) apellidoInput else ""
            val nombreCompleto = "$nombreFinal $apellidoFinal".trim()

            // Pasar los datos a la siguiente Activity (Formulario GAD-7) y finalizar esta
            val intent = Intent(this, FormularioActivity::class.java).apply {
                putExtra("EXTRA_NOMBRE", nombreCompleto)
            }
            startActivity(intent)
            finish() // Cierra la MainActivity para que no se pueda volver atrás con el botón del celular
        }
    }

    private fun configurarValidaciones() {
        val textWatcherGeneral = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                verificarFormularioCompleto()
            }
        }

        etNombre.addTextChangedListener(textWatcherGeneral)
        etApellido.addTextChangedListener(textWatcherGeneral)
        etCorreo.addTextChangedListener(textWatcherGeneral)
        cbTerminos.setOnCheckedChangeListener { _, _ -> verificarFormularioCompleto() }

        // TextWatcher específico para la contraseña (efecto "chuleo" en tiempo real)
        etContrasena.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validarRequisitosContrasena(s.toString())
                verificarFormularioCompleto()
            }
        })
    }

    private fun validarRequisitosContrasena(pass: String) {
        val tieneLargo = pass.length >= 8
        actualizarEstadoRequisito(tvReqLargo, tieneLargo, "Mínimo 8 caracteres")

        val tieneMayuscula = pass.any { it.isUpperCase() }
        actualizarEstadoRequisito(tvReqMayus, tieneMayuscula, "Al menos una letra mayúscula")

        val tieneNumero = pass.any { it.isDigit() }
        actualizarEstadoRequisito(tvReqNum, tieneNumero, "Al menos un número")
    }

    private fun actualizarEstadoRequisito(tv: TextView, cumplido: Boolean, texto: String) {
        if (cumplido) {
            tv.text = "✓ $texto"
            tv.setTextColor(ContextCompat.getColor(this, R.color.success))
        } else {
            tv.text = "○ $texto"
            tv.setTextColor(ContextCompat.getColor(this, R.color.error))
        }
    }

    private fun validarNombresYApellidos(): Boolean {
        val nombre = etNombre.text.toString().trim()
        val apellido = etApellido.text.toString().trim()
        val soloLetras = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")

        if (!soloLetras.matcher(nombre).matches() || !soloLetras.matcher(apellido).matches()) {
            Toast.makeText(this, "Ingresa un nombre o apellido válido (solo letras)", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validarCorreoElectronico(): Boolean {
        val correo = etCorreo.text.toString().trim()
        val patronCorreo = android.util.Patterns.EMAIL_ADDRESS

        if (!patronCorreo.matcher(correo).matches()) {
            Toast.makeText(this, "Ingresa un correo válido", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verificarFormularioCompleto() {
        val nombre = etNombre.text.toString().trim()
        val apellido = etApellido.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val pass = etContrasena.text.toString()
        val terminosAceptados = cbTerminos.isChecked

        val passValida = pass.length >= 8 && pass.any { it.isUpperCase() } && pass.any { it.isDigit() }

        btnRegistro.isEnabled = nombre.isNotEmpty() && apellido.isNotEmpty() &&
                correo.isNotEmpty() && passValida && terminosAceptados
    }

    private fun iniciarAnimacion() {
        ivLogo.scaleX = 0.2f
        ivLogo.scaleY = 0.2f
        ivLogo.alpha = 0f

        ivLogo.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .alpha(1.0f)
            .setDuration(1200)
            .withEndAction {
                iniciarMovimientoRespiracion()
            }
            .start()

        tvLogo.translationY = 40f
        tvLogo.alpha = 0f
        tvLogo.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(200)
            .start()

        tvBienvenida.translationY = 40f
        tvBienvenida.alpha = 0f
        tvBienvenida.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(1200)
            .setStartDelay(400)
            .start()
    }

    private fun iniciarMovimientoRespiracion() {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.08f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.08f)

        val animador = ObjectAnimator.ofPropertyValuesHolder(ivLogo, scaleX, scaleY).apply {
            duration = 2500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }
        animador.start()
    }
}