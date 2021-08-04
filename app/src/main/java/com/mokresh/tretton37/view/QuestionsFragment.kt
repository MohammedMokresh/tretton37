package com.mokresh.tretton37.view

import android.os.Bundle
import android.view.View
import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.BaseFragment
import com.mokresh.tretton37.databinding.FragmentQuestionsBinding
import com.mokresh.tretton37.utils.UIEvent


class QuestionsFragment : BaseFragment<FragmentQuestionsBinding, QuestionsViewModel>
    (R.layout.fragment_questions, QuestionsViewModel::class) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestions()

    }


    override fun onUIEventTriggered(event: UIEvent) {
        when (event) {
            is UIEvent.RenderQuestionsViewPager -> {
                binding.questionsViewPager.adapter = QuestionsAdapter(items = event.result!!)

            }
        }
    }
}