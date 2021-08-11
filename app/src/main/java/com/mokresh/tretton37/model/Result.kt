package com.mokresh.tretton37.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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

) : Parcelable {
    fun enhancedQuestion(): String? {
        return question?.replace("&quot;", "\"")
    }
}