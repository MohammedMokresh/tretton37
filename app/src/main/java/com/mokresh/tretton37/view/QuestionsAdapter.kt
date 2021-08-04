package com.mokresh.tretton37.view

import android.view.ViewGroup
import android.widget.LinearLayout
import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.GenericAdapter
import com.mokresh.tretton37.base.GenericViewHolder
import com.mokresh.tretton37.databinding.QuestionItemBinding
import com.mokresh.tretton37.model.Result


class QuestionsAdapter(items: List<Result?>) :
    GenericAdapter<Result, QuestionItemBinding>(layoutId = R.layout.question_item, items = items) {

    override fun onBindViewHolder(holder: GenericViewHolder<Result, QuestionItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItems()?.get(position)
        holder.binding.root.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        val answersList: ArrayList<String> = item?.incorrectAnswers!!
        answersList.add(item.correctAnswer!!)
        answersList.shuffle()

        holder.binding.questionRecyclerView.adapter = AnswersAdapter(answersList)
    }
}