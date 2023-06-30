package com.ebinumer.kiemusictest.data.model

data class TracksResponse(
    val tracks: List<TrackData>
)

data class TrackData(
    val id: String,
    val name: String,
    val artists: List<ArtistData>
)

data class ArtistData(
    val id: String,
    val name: String
)
