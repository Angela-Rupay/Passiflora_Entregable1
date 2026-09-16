package com.example.prueba_emulador.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.prueba_emulador.network.modelos.YoutubeSearchResponse

interface YoutubeApiService {

    @GET("search")
    fun buscarVideos(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("part") part: String = "snippet",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 1,
        @Query("videoEmbeddable") videoEmbeddable: String = "true",
        @Query("relevanceLanguage") relevanceLanguage: String = "es"
    ): Call<YoutubeSearchResponse>
}