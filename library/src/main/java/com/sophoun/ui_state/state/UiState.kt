package com.sophoun.ui_state.state

abstract class UiState {
    override fun equals(other: Any?): Boolean {
        return if (other == null) {
            false
        } else {
            javaClass.simpleName == other.javaClass.simpleName
        }
    }
}