package com.mokresh.tretton37.model


import com.google.gson.annotations.SerializedName

data class QuestionsResponseBody(
    @SerializedName("response_code")
    val responseCode: Int?,
    @SerializedName("results")
    val results: List<Result>?
)