package com.sophoun.utils

import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to `postValue()` or `setValue()`.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val eventWrapperList = mutableListOf<Pair<LifecycleOwner, EventWrapper<T>>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = Pair(owner, EventWrapper(observer))
        eventWrapperList.add(wrapper)
        super.observe(owner, observer)
    }

    override fun postValue(value: T) {
        notifyNewValue(value)
    }

    override fun setValue(value: T) {
        notifyNewValue(value)
    }

    private fun notifyNewValue(value: T) {
        eventWrapperList.removeAll { it.first.lifecycle.currentState == Lifecycle.State.DESTROYED }
        eventWrapperList.forEach {
            it.second.newValue()
            it.second.onChanged(value)
        }
    }

    private class EventWrapper<T>(private val observer: Observer<in T>) : Observer<T> {
        private val pending = AtomicBoolean(false)

        override fun onChanged(t: T?) {
            if (pending.get()) {
                pending.set(false)
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending.set(true)
        }
    }
}