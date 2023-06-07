package com.everybodv.habibulquran.data.remote.response

import com.google.gson.annotations.SerializedName

data class SurahByIdResponse(
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)