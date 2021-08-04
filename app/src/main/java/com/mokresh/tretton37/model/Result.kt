package com.mokresh.tretton37.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("category")
    var category: String? = "",
    @SerializedName("correct_answer")
    var correctAnswer: String? = "",
    @SerializedName("difficulty")
    var difficulty: String? = "",
    @SerializedName("incorrect_answers")
    var incorrectAnswers: ArrayList<String> = ArrayList(),
    @SerializedName("question")
    var question: String? = "",
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("answer_status")
    var answerStatus: String? = ""

) {
    fun enhancedQuestion(): String? {
        return question?.replace("&quot;", "\"")
    }
}