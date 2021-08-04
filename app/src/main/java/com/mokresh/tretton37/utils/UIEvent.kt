package com.mokresh.tretton37.utils

import com.mokresh.tretton37.model.Result

abstract class UIEvent {

    data class RenderQuestionsViewPager(val result: ArrayList<Result>?) : UIEvent()

    data class AnswerClicked(val answer: String, val questionPosition: Int) : UIEvent()

    object OnTimerStarted : UIEvent()
    object OnTimerFinished : UIEvent()


    object OnBackPressed : UIEvent()


}