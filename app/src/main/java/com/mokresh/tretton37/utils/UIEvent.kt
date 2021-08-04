package com.mokresh.tretton37.utils

import com.mokresh.tretton37.model.Result

abstract class UIEvent {

    data class RenderQuestionsViewPager(val result: List<Result>?) : UIEvent()

    object OnBackPressed : UIEvent()


}