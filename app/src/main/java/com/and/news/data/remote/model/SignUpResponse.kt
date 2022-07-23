package com.and.news.data.remote.model


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)