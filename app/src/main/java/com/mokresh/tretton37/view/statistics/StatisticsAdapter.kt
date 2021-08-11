package com.mokresh.tretton37.view.statistics

import com.mokresh.tretton37.R
import com.mokresh.tretton37.base.GenericAdapter
import com.mokresh.tretton37.databinding.StatisticsItemBinding
import com.mokresh.tretton37.model.Result

class StatisticsAdapter(items: List<Result?>) :
    GenericAdapter<Result, StatisticsItemBinding>(layoutId = R.layout.statistics_item, items = items) {

}