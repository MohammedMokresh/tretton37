package com.mokresh.tretton37.model


import com.google.gson.annotations.SerializedName

data class QuestionsResponseBody(
    @SerializedName("response_code")
    var responseCode: Int?,
    @SerializedName("results")
    var results: ArrayList<Result>?
)