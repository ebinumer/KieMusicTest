package com.ebinumer.kiemusictest.data.model

import com.google.gson.annotations.SerializedName

data class GenreAllResponse(
    @SerializedName("genres") var genres: ArrayList<Genres> = arrayListOf(),
    @SerializedName("genre-offset") var genreOffset: Int? = null,
    @SerializedName("genre-count") var genreCount: Int? = null
)

data class Genres(

    @SerializedName("id") var id: String? = null,
    @SerializedName("disambiguation") var disambiguation: String? = null,
    @SerializedName("name") var name: String? = null

)