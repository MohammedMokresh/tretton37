package com.mokresh.tretton37.view.statistics

import android.os.Bundle
import android.view.View
import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.BaseFragment
import com.mokresh.tretton37.databinding.FragmentStatisticsBinding
import com.mokresh.tretton37.utils.UIEvent
import com.mokresh.tretton37.view.QuestionsViewModel


class StatisticsFragment : BaseFragment<FragmentStatisticsBinding, QuestionsViewModel>
    (R.layout.fragment_statistics, QuestionsViewModel::class) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val results = StatisticsFragmentArgs.fromBundle(requireArguments()).resultsList
            binding.statisticsRecyclerView.adapter = StatisticsAdapter(results.toList())

        }

    }

    override fun onUIEventTriggered(event: UIEvent) {

    }

}