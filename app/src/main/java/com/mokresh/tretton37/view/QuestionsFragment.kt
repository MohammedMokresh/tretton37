package com.mokresh.tretton37.view

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.BaseFragment
import com.mokresh.tretton37.databinding.FragmentQuestionsBinding
import com.mokresh.tretton37.utils.Constants
import com.mokresh.tretton37.utils.UIEvent
import java.text.SimpleDateFormat
import java.util.*


class QuestionsFragment : BaseFragment<FragmentQuestionsBinding, QuestionsViewModel>
    (R.layout.fragment_questions, QuestionsViewModel::class) {
    private var countDownTimer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestions()
        binding.questionsViewPager.isUserInputEnabled = false

    }


    override fun onUIEventTriggered(event: UIEvent) {
        when (event) {
            is UIEvent.RenderQuestionsViewPager -> {
                val tempList = event.result
                tempList?.removeLast()
                binding.questionsViewPager.adapter = QuestionsAdapter(items = tempList!!)
                viewModel.resultsList = event.result

            }
            is UIEvent.AnswerClicked -> {
                countDownTimer?.cancel()
                when {
                    event.answer == viewModel.resultsList[event.questionPosition].correctAnswer -> {
                        viewModel.resultsList[event.questionPosition].answerStatus =
                            Constants.AnswerStatus.CORRECT.status
                    }
                    event.answer.isEmpty() -> {
                        viewModel.resultsList[event.questionPosition].answerStatus =
                            Constants.AnswerStatus.NOT_ANSWERED.status
                    }
                    else -> {
                        viewModel.resultsList[event.questionPosition].answerStatus =
                            Constants.AnswerStatus.INCORRECT.status
                    }
                }
                binding.questionsViewPager.currentItem = event.questionPosition + 1
                viewModel.resultsList.forEach {

                    Log.e("aoisd", it.answerStatus + it.question)
                }
            }
            is UIEvent.OnTimerStarted -> {
                startTheTimer()
            }
        }
    }

    private fun startTheTimer() {
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text =
                    "seconds remaining: " + SimpleDateFormat("mm:ss").format(Date(millisUntilFinished));

            }

            override fun onFinish() {
                viewModel.resultsList[binding.questionsViewPager.currentItem].answerStatus =
                    Constants.AnswerStatus.NOT_ANSWERED.status
                binding.questionsViewPager.currentItem = binding.questionsViewPager.currentItem + 1

            }
        }
        countDownTimer?.start()

    }
}