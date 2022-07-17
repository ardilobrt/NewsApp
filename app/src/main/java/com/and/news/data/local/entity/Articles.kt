package com.and.news.data.local.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "articles")
class Articles(
    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @field:ColumnInfo(name = "sourceName")
    val sourceName: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String,

    @field:ColumnInfo(name = "isBookmarked")
    val isBookmarked: Boolean
) : Parcelable