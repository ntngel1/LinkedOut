package com.github.ntngel1.linkedout.lib_utils

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base class for MVI-like ViewModel.
 *
 * @param S is type of state. Can be [Nothing] if no state used
 * @param E is type of events. Can be [Nothing] if no events used
 */
abstract class MviViewModel<S : Any, E : Any> : ViewModel() {

    private val _state = MutableLiveData<S>()
    val state: LiveData<S> = _state

    private val _events = SingleLiveEvent<E>()
    val events: LiveData<E> = _events

    protected val currentState: S
        get() = requireNotNull(_state.value)

    @MainThread
    protected fun updateState(state: S) {
        _state.value = state
    }

    @MainThread
    protected fun updateState(transform: (currentState: S) -> S) {
        val currentState = requireNotNull(state.value)
        _state.value = transform.invoke(currentState)
    }

    @MainThread
    protected fun sendEvent(event: E) {
        _events.value = event
    }
}