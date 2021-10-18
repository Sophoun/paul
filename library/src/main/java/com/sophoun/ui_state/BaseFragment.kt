package com.sophoun.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sophoun.ui.state.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    /**
     * Initialize this property to ViewModel factory to sub class
     */
    var viewModelFactory: ViewModelProvider.Factory? = null

    private val _job = Job()
    override val coroutineContext: CoroutineContext
        get() = _job + Dispatchers.IO

    val baseActivity: BaseActivity by lazy { WeakReference(activity).get() as BaseActivity }

    /**
     * [onBackPress] Callback for activity and fragment
     */
    private val backPressedDispatcher = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!onBackPress()) {
                baseActivity.onBackPress()
            }
        }
    }

    /**
     * Sub class musts provide layout resource
     * by override this method
     */
    @LayoutRes abstract fun layout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedDispatcher)
    }

    /**
     * Attach ViewModel with UiState changed
     */
    fun setupWithViewModel(vararg viewModel: BaseViewModel) {
        viewModel.forEach {
            it.uiState().observe(viewLifecycleOwner, Observer { uiState ->
                onStateChanged(uiState)
            })
        }
    }

    /**
     * UI must override this method to update their
     * UI State
     */
    @CallSuper
    open fun onStateChanged(state: UiState) {}

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        // Add listener to the animation of fragment transition
        return if (nextAnim != 0x0) {
            val animation = AnimationUtils.loadAnimation(context?.applicationContext, nextAnim)
            animation.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) { onFragmentAnimationStart(animation) }
                override fun onAnimationRepeat(animation: Animation?) { onFragmentAnimationRepeat(animation) }
                override fun onAnimationEnd(animation: Animation?) { onFragmentAnimationEnd(animation) }
            })
            animation
        } else {
            super.onCreateAnimation(transit, enter, nextAnim)
        }
    }

    /**
     * Listen to fragment animation start
     */
    open fun onFragmentAnimationStart(animation: Animation?) {}

    /**
     * Listen to fragment animation repeat
     */
    open fun onFragmentAnimationRepeat(animation: Animation?) {}

    /**
     * Listen to fragment animation end
     */
    open fun onFragmentAnimationEnd(animation: Animation?) {}


    /**
     * Cancel coroutine job
     */
    override fun onDestroy() {
        super.onDestroy()
        _job.cancel()
    }

    @CallSuper
    open fun onBackPress(): Boolean {
        return false
    }
}
