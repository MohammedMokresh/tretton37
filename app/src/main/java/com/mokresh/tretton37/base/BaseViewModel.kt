package com.mokresh.tretton37.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.mokresh.tretton37.utils.UIEvent
import com.dansdev.libeventpipe.EventPipe

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    val isDataEmpty = ObservableBoolean(false)


    fun <T : UIEvent> publishUIEvent(event: T) {
        EventPipe.send(event)
    }

    fun onBackPressed() {
        publishUIEvent(UIEvent.OnBackPressed)
    }

}
