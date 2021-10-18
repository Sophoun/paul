package com.sophoun.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sophoun.ui.state.state.UiState
import com.sophoun.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    /**
     * Coroutine job
     */
    private var _job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = _job + Dispatchers.IO

    /**
     * UiState broadcast channel
     */
    private val _uiStateLiveEvent = SingleLiveEvent<UiState>()
    fun uiState(): LiveData<UiState> = _uiStateLiveEvent

    override fun onCleared() {
        super.onCleared()
        _job.cancel()
    }

    /**
     * Notify UiState
     */
    fun setState(uiState: UiState) {
        launch(Dispatchers.Main) {
            _uiStateLiveEvent.setValue(uiState)
        }
    }
}