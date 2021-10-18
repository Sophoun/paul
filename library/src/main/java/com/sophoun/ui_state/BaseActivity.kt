package com.sophoun.ui.state

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import com.sophoun.ui.state.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Note:
 * - Do not override onBackPressed() method on each subclass
 * do this to prevent unexpected error of Navigation Component
 */
abstract class BaseActivity : FragmentActivity(), CoroutineScope {
    /**
     * Initialize this property to provide fragment factory to sub class
     */
    var fragmentFactory: FragmentFactory? = null

    private val _job = Job()
    override val coroutineContext: CoroutineContext
        get() = _job + Dispatchers.IO

    /**
     * Sub class musts provide layout resource
     * by override this method
     */
    @LayoutRes abstract fun layout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentFactory?.let {
            supportFragmentManager.fragmentFactory = it
        }
        super.onCreate(savedInstanceState)
        setContentView(layout())
    }

    /**
     * Attach ViewModel with UiState changed
     */
    fun setupWithViewModel(vararg viewModel: BaseViewModel) {
        viewModel.forEach {
            it.uiState().observe(this@BaseActivity, Observer { uiState ->
                onStateChanged(uiState)
            })
        }
    }

    /**
     * Client need to override this method to
     * update their ui state
     */
    open fun onStateChanged(state: UiState) { }

    /**
     * Use this for on backpress.
     *
     * Note:
     * - Do not override the default onBackPressed() method
     * because it leads to unexpected error
     */
    @CallSuper
    open fun onBackPress(): Boolean {
        return false
    }

    /**
     * Cancel coroutine job
     */
    override fun onDestroy() {
        _job.cancel()
        super.onDestroy()
    }
}
