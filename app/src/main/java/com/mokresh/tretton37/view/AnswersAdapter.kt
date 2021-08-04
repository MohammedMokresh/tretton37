package com.mokresh.tretton37.view

import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.GenericAdapter
import com.mokresh.tretton37.databinding.AnswerItemBinding

class AnswersAdapter(items: ArrayList<String>) :
    GenericAdapter<String, AnswerItemBinding>(layoutId = R.layout.answer_item, items = items)