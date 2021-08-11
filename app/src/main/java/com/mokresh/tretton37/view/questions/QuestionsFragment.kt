package com.mokresh.tretton37.view.questions

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.BaseFragment
import com.mokresh.tretton37.databinding.FragmentQuestionsBinding
import com.mokresh.tretton37.utils.Constants
import com.mokresh.tretton37.utils.UIEvent
import com.mokresh.tretton37.view.QuestionsViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class QuestionsFragment : BaseFragment<FragmentQuestionsBinding, QuestionsViewModel>
    (R.layout.fragment_questions, QuestionsViewModel::class) {
    private var countDownTimer: CountDownTimer? = null
    private var questionAdapter: QuestionsAdapter? = null
    private var timeLeft: Long = 0
    private var countdownPeriod: Long = 10000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewModel.getQuestions()
        binding.questionsViewPager.isUserInputEnabled = false

    }


    override fun onUIEventTriggered(event: UIEvent) {
        when (event) {
            is UIEvent.RenderQuestionsViewPager -> {
                startTheTimer(0)

                viewModel.resultsList = event.result!!

                questionAdapter = QuestionsAdapter(items = viewModel.resultsList)

                binding.questionsViewPager.adapter = questionAdapter
                binding.questionsViewPager.currentItem = 0

            }
            is UIEvent.AnswerClicked -> {
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

                navigateToStatistics()

                questionAdapter?.notifyItemChanged(binding.questionsViewPager.currentItem)

                viewModel.resultsList.forEach {


                }
            }

            is UIEvent.FiftyFiftyClicked -> {
                binding.fiftyFiftyButton.isEnabled = false
                binding.fiftyFiftyButton.alpha = 0.5f
                val item =
                    viewModel.resultsList[binding.questionsViewPager.currentItem].incorrectAnswers.random()
                viewModel.resultsList[binding.questionsViewPager.currentItem].incorrectAnswers =
                    ArrayList()
                viewModel.resultsList[binding.questionsViewPager.currentItem].incorrectAnswers.add(
                    item
                )

                questionAdapter?.notifyItemChanged(binding.questionsViewPager.currentItem)

                viewModel.resultsList.forEach {
                }

            }
            is UIEvent.ReplaceQuestionClicked -> {
                binding.replaceQuestionButton.isEnabled = false
                binding.replaceQuestionButton.alpha = 0.5f

                val item = viewModel.resultsList[viewModel.resultsList.lastIndex]
                viewModel.resultsList[binding.questionsViewPager.currentItem] = item

                questionAdapter?.notifyItemChanged(binding.questionsViewPager.currentItem)
                viewModel.resultsList.forEach {
                }


            }

            is UIEvent.PlusTenClicked -> {
                binding.plusTenButton.isEnabled = false
                binding.plusTenButton.alpha = 0.5f
                startTheTimer(timeLeft)
            }


        }
    }

    private fun startTheTimer(addedTime: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(countdownPeriod + addedTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                binding.timerTextView.text =
                    "seconds remaining: " + SimpleDateFormat("mm:ss").format(
                        Date(
                            millisUntilFinished
                        )
                    )

            }

            override fun onFinish() {
                viewModel.resultsList[binding.questionsViewPager.currentItem].answerStatus =
                    Constants.AnswerStatus.NOT_ANSWERED.status
                binding.questionsViewPager.currentItem = binding.questionsViewPager.currentItem + 1
                navigateToStatistics()

            }
        }
        countDownTimer?.start()

    }

    fun navigateToStatistics() {
        countDownTimer?.cancel()
        if (binding.questionsViewPager.currentItem == viewModel.resultsList.size - 1) {
            viewModel.resultsList.removeLast()
            val directions =
                QuestionsFragmentDirections.actionQuestionsFragmentToStatisticsFragment(
                    viewModel.resultsList.toTypedArray()
                )
            directions.let { findNavController().navigate(it) }

            //last item
        } else {
            startTheTimer(0)
        }

    }
}