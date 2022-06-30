package com.and.news.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String?
) : Parcelable