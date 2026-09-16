package com.example.prueba_emulador

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.prueba_emulador.network.modelos.RetrofitClient
import com.example.prueba_emulador.network.modelos.YoutubeSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

class EjerciciosRespiracionActivity : AppCompatActivity() {

    //private lateinit var webViewEjercicio: WebView
    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var progressEjercicio: View
    private lateinit var tvEstado: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicios_respiracion)



        //webViewEjercicio = findViewById(R.id.webview_ejercicio)
        progressEjercicio = findViewById(R.id.progress_ejercicio)
        tvEstado = findViewById(R.id.tv_estado_ejercicio)
        youtubePlayerView = findViewById(R.id.youtube_player)
        lifecycle.addObserver(youtubePlayerView)

        // Llega desde ResultadoActivity al lanzar este Intent (ver más abajo)
        val puntajeTotal = intent.getIntExtra("PUNTAJE_TOTAL", 0)

        val consultaBusqueda = obtenerConsultaSegunNivel(puntajeTotal)
        buscarVideoEjercicio(consultaBusqueda)
    }

    /**
     * Cortes estándar de la escala GAD-7:
     * 0-4 mínimo, 5-9 leve, 10-14 moderado, 15-21 severo.
     */
    private fun obtenerConsultaSegunNivel(puntaje: Int): String {
        return when {
            puntaje <= 4 -> "ejercicio de respiración corto para relajarse animado"
            puntaje in 5..9 -> "ejercicio de respiración guiada para la ansiedad"
            puntaje in 10..14 -> "ejercicio de respiración profunda para calmar la ansiedad guiado"
            else -> "técnica de respiración para ansiedad severa guiada paso a paso"
        }
    }

    private fun buscarVideoEjercicio(consulta: String) {
        progressEjercicio.visibility = View.VISIBLE
        tvEstado.visibility = View.GONE

        RetrofitClient.instance.buscarVideos(
            query = consulta,
            apiKey = BuildConfig.YOUTUBE_API_KEY
        ).enqueue(object : Callback<YoutubeSearchResponse> {

            override fun onResponse(
                call: Call<YoutubeSearchResponse>,
                response: Response<YoutubeSearchResponse>
            ) {
                progressEjercicio.visibility = View.GONE
                val videoId = response.body()?.items?.firstOrNull()?.id?.videoId

                if (response.isSuccessful && videoId != null) {
                    mostrarVideo(videoId)
                } else {
                    mostrarError()
                }
            }

            override fun onFailure(call: Call<YoutubeSearchResponse>, t: Throwable) {
                progressEjercicio.visibility = View.GONE
                mostrarError()
            }
        })
    }

    private fun mostrarVideo(videoId: String) {
        youtubePlayerView.visibility = View.VISIBLE

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                player.loadVideo(videoId, 0f)
            }
        })
    }

   /* private fun mostrarVideo(videoId: String) {

        webViewEjercicio.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }

        webViewEjercicio.webChromeClient = android.webkit.WebChromeClient()
        webViewEjercicio.webViewClient = WebViewClient()
        webViewEjercicio.visibility = View.VISIBLE

        val = """
        <!DOCTYPE >
        <html>
        <body style="margin:0;background:black;">
            <iframe
                width="100%"
                height="100%"
                src="https://www.youtube.com/embed/$videoId?autoplay=1&playsinline=1&rel=0"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen>
            </iframe>
        </body>
        </html>
    """.trimIndent()

        webViewEjercicio.loadDataWithBaseURL(
            "https://www.youtube.com",
            ,
            "text/html",
            "UTF-8",
            null
        )
    }*/
    /*private fun mostrarVideo(videoId: String) {
        val settings = webViewEjercicio.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false

        // Simula un navegador para evitar el bloqueo de reproducción
        settings.userAgentString = "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36"

        webViewEjercicio.webViewClient = WebViewClient()
        webViewEjercicio.visibility = View.VISIBLE

        // Definimos el ID fijo que enviaste con los parámetros del iframe incluidos
        val idTarget = "IShkpOm63gg" // Si quieres hacerlo dinámico más adelante, cambia esto por 'videoId'
        val urlEmbed = "https://www.youtube.com/embed/$idTarget?autoplay=1&modestbranding=1&rel=0&enablejsapi=1"

        // Cabeceras HTTP que simulan el 'referrerpolicy="strict-origin-when-cross-origin"' del iframe
        val extraHeaders = HashMap<String, String>()
        extraHeaders["Referer"] = "https://www.youtube.com"
        extraHeaders["Origin"] = "https://www.youtube.com"

        webViewEjercicio.loadUrl(urlEmbed, extraHeaders)
    }*/

   /* private fun mostrarVideo(videoId: String) {
        webViewEjercicio.settings.javaScriptEnabled = true
        webViewEjercicio.webViewClient = WebViewClient()
        webViewEjercicio.visibility = View.VISIBLE
        webViewEjercicio.loadUrl("https://www.youtube.com/embed/IShkpOm63gg?si=QqPisBSpms9dBajy")
    }*/

    private fun mostrarError() {
        youtubePlayerView .visibility = View.GONE
        tvEstado.visibility = View.VISIBLE
        tvEstado.text = "No pudimos cargar el ejercicio en este momento. Revisa tu conexión e inténtalo de nuevo."
    }
}