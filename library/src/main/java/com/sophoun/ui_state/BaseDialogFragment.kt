package com.sophoun.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sophoun.ui.state.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

abstract class BaseDialogFragment : DialogFragment(), CoroutineScope {

    /**
     * Initialize this property to provide ViewModel factory to sub class
     */
    var viewModelFactory: ViewModelProvider.Factory? = null

    private val _job = Job()
    override val coroutineContext: CoroutineContext
        get() = _job + Dispatchers.IO

    val baseActivity: BaseActivity by lazy { WeakReference(activity).get() as BaseActivity }

    /**
     * Sub class musts provide layout resource
     * by override this method
     */
    @LayoutRes
    abstract fun layout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout(), container, false)
    }

    fun setupWithViewModel(vararg viewModel: BaseViewModel) {
        viewModel.forEach {
            it.uiState().observe(this@BaseDialogFragment, Observer { uiState ->
                onStateChanged(uiState)
            })
        }
    }

    /**
     * UI must override this method to update their
     * UI State
     */
    open fun onStateChanged(state: UiState) {}

    /**
     * Cancel coroutine job
     */
    override fun onDestroy() {
        super.onDestroy()
        _job.cancel()
    }
}