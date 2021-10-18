package com.sophoun.ui.state.state

abstract class UiState {
    override fun equals(other: Any?): Boolean {
        return if (other == null) {
            false
        } else {
            javaClass.simpleName == other.javaClass.simpleName
        }
    }
}