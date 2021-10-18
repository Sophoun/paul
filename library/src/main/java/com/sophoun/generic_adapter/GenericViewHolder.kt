package com.sophoun.generic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.sophoun.ui.state.BaseViewModel
import com.sophoun.ui.state.state.UiState

abstract class GenericViewHolder<T>
    constructor(
        parent: ViewGroup,
        @LayoutRes layoutRes: Int
    ) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry
    override fun getLifecycle(): Lifecycle = lifecycleRegistry
    private fun initLifecycle() {
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    @CallSuper
    open fun bind(data: T) {
        initLifecycle()
    }

    /**
     * Attach ViewModel with UiState changed
     */
    fun setupWithViewModel(vararg viewModel: BaseViewModel) {
        viewModel.forEach {
            it.uiState().observe(this, androidx.lifecycle.Observer<UiState> { uiState ->
                onStateChanged(uiState)
            })
        }
    }

    /**
     * Client need to override this method to
     * update their ui state
     */
    open fun onStateChanged(state: UiState) {}
}