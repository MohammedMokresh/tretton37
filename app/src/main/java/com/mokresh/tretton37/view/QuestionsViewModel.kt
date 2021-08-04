package com.mokresh.tretton37.view

import androidx.lifecycle.viewModelScope
import com.mokresh.tretton37.QuestionsRepository
import com.mokresh.tretton37.base.BaseViewModel
import com.mokresh.tretton37.utils.UIEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuestionsViewModel(private val questionsRepository: QuestionsRepository) : BaseViewModel() {

    fun getQuestions() {
        viewModelScope.launch {
            questionsRepository.getQuestions().collect {
                publishUIEvent(UIEvent.RenderQuestionsViewPager(it.body()?.results))
            }
        }
    }


}