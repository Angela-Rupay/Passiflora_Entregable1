package com.example.prueba_emulador.network.modelos

data class YoutubeSearchResponse(
    val items: List<YoutubeItem>
)

data class YoutubeItem(
    val id: YoutubeVideoId,
    val snippet: YoutubeSnippet
)

data class YoutubeVideoId(
    val videoId: String
)

data class YoutubeSnippet(
    val title: String
)