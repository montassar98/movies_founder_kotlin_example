package com.vneuron.mvpkotlinexample.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Entity(tableName = "test2")
open class Movie() : Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "id")
    var id: Int? = null

    @SerializedName("vote_count")
    @Expose
    @ColumnInfo(name = "vote_count")
    var voteCount: Int? = null

    @ColumnInfo(name = "video")
    @SerializedName("video")
    @Expose
    var video: Boolean? = null

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    @Expose
    var posterPath : String? = null

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    @Expose
    var adult : Boolean? = null

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    @Expose
    var backdropPath : String? = null

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    @Expose
    var originalLanguage : String? = null

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    @Expose
    var originalTitle : String? = null

    /**list place */
    @SerializedName("genre_ids")
    @ColumnInfo(name = "genre_ids")
    @Expose
    var genreIds : List<Int>? = null

    @SerializedName("title")
    @ColumnInfo(name = "title")
    @Expose
    var title : String? = null

    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    @Expose
    var voteAverage : Float? = null

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    @Expose
    var overview : String? = null

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    @Expose
    var releaseDate : String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        voteCount = parcel.readValue(Int::class.java.classLoader) as? Int
        video = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        posterPath = parcel.readString()
        adult = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        backdropPath = parcel.readString()
        originalLanguage = parcel.readString()
        originalTitle = parcel.readString()
        title = parcel.readString()
        popularity = parcel.readValue(Float::class.java.classLoader) as? Float
        voteAverage = parcel.readValue(Float::class.java.classLoader) as? Float
        overview = parcel.readString()
        releaseDate = parcel.readString()
    }

    /**
     * Constructor for manually added movies
     *
     */
    constructor(title: String, releaseDate: String, posterPath: String) :this(){
        this.title = title
        this.releaseDate = releaseDate
        this.posterPath = posterPath
    }

    /**
     *
     * @param genreIds
     * @param id
     * @param title
     * @param releaseDate
     * @param overview
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param video
     * @param popularity
     */

    constructor(id: Int?, voteCount: Int?, video: Boolean?, voteAverage: Float?, title: String, popularity: Float?, posterPath: String, originalLanguage: String, originalTitle: String, genreIds: List<Int>, backdropPath: String, adult: Boolean?, overview: String, releaseDate: String) : this() {
        this.voteCount = voteCount
        this.video = video
        this.voteAverage = voteAverage
        this.title = title
        this.popularity = popularity
        this.posterPath = posterPath
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.genreIds = genreIds
        this.backdropPath = backdropPath
        this.adult = adult
        this.overview = overview
        this.releaseDate = releaseDate
    }

    override fun toString(): String {
        return "title = $title" +
                "overview = $overview"
    }




}