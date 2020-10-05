package com.vneuron.mvpkotlinexample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity( tableName = "movies")
data class MovieTest(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id:Int?,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title:String?,
    @ColumnInfo(name = "description")
    @SerializedName("desc")
    var description:String?
    )
{
    override fun toString(): String {
        return "id =  ${id?:"null"} ; title = $title ; description = $description"
    }
}