package com.ebinumer.kiemusictest.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("created") var created: String? = null,
    @SerializedName("count") var count: Int? = null,
    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("recordings") var recordings: ArrayList<Recordings> = arrayListOf()
)


data class Aliases(

    @SerializedName("sort-name") var sortName: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("locale") var locale: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("primary") var primary: String? = null,
    @SerializedName("begin-date") var beginDate: String? = null,
    @SerializedName("end-date") var endDate: String? = null

)

data class Artist(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("sort-name") var sortName: String? = null,
    @SerializedName("disambiguation") var disambiguation: String? = null,
    @SerializedName("aliases") var aliases: ArrayList<Aliases> = arrayListOf()

)

data class ArtistCredit(

    @SerializedName("joinphrase")
    var joinphrase: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("artist")
    var artist: Artist? = Artist()

)


data class ReleaseGroup(

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("type-id")
    var typeId: String? = null,
    @SerializedName("primary-type-id")
    var primaryTypeId: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("primary-type")
    var primaryType: String? = null,
    @SerializedName("secondary-types")
    var secondaryTypes: ArrayList<String> = arrayListOf(),
    @SerializedName("secondary-type-ids")
    var secondaryTypeIds: ArrayList<String> = arrayListOf()

)


data class Area(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("sort-name") var sortName: String? = null,
    @SerializedName("iso-3166-1-codes") var isoCodes: ArrayList<String> = arrayListOf()

)

data class ReleaseEvents(

    @SerializedName("date")
    var date: String? = null,
    @SerializedName("area")
    var area: Area? = Area()

)

data class Track(

    @SerializedName("id") var id: String? = null,
    @SerializedName("number") var number: String? = null,
    @SerializedName("title") var title: String? = null

)

data class Media(

    @SerializedName("position") var position: Int? = null,
    @SerializedName("format") var format: String? = null,
    @SerializedName("track") var track: ArrayList<Track> = arrayListOf(),
    @SerializedName("track-count") var trackCount: Int? = null,
    @SerializedName("track-offset") var trackOffset: Int? = null

)

data class Releases(

    @SerializedName("id") var id: String? = null,
    @SerializedName("status-id") var statusId: String? = null,
    @SerializedName("count") var count: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("artist-credit") var artistCredit: ArrayList<ArtistCredit> = arrayListOf(),
    @SerializedName("release-group")
    var releaseGroup: ReleaseGroup? = ReleaseGroup(),
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("release-events")
    var releaseEvents: ArrayList<ReleaseEvents> = arrayListOf(),
    @SerializedName("track-count")
    var trackCount: Int? = null,
    @SerializedName("media")
    var media: ArrayList<Media> = arrayListOf()

)

data class Recordings(

    @SerializedName("id") var id: String? = null,
    @SerializedName("score") var score: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: String? = null,
    @SerializedName("artist-credit") var artistCredit: ArrayList<ArtistCredit> = arrayListOf(),
    @SerializedName("first-release-date") var firstReleaseDate: String? = null,
    @SerializedName("releases") var releases: ArrayList<Releases> = arrayListOf()

)