package com.mokresh.tretton37.view

import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.GenericAdapter
import com.mokresh.tretton37.databinding.AnswerItemBinding
import com.mokresh.tretton37.utils.UIEvent

class AnswersAdapter(private val questionPosition: Int, items: ArrayList<String>) :
    GenericAdapter<String, AnswerItemBinding>(layoutId = R.layout.answer_item, items = items) {

    override fun onItemClick(item: String) {
        super.onItemClick(item)
        publishUIEvent(UIEvent.AnswerClicked(answer = item, questionPosition = questionPosition))
    }
}