package com.sophoun.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object SoftKeyboard {

    /**
     * Give InputMethodManager object
     * @param context
     */
    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    /**
     * Show soft keyboard
     * @param view Require focusable view (e.g: EditText)
     */
    fun show(view: View) {
        if (view.requestFocus()) {
            getInputMethodManager(view.context).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    /**
     * Show soft keyboard
     * @param view Require focusable view (e.g: EditText)
     */
    fun showWithDialog(view: View) {
        if (view.requestFocus()) {
            getInputMethodManager(view.context).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    /**
     * Hide soft keyboard
     * @param view
     */
    fun hide(view: View) {
        getInputMethodManager(view.context).hideSoftInputFromWindow(view.windowToken, 0)
    }
}