package com.example.nod.common

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

/**
 * Function to display a [Snackbar] with a desired [message]
 * for a desired [duration] in a [ViewGroup].
 */
fun ViewGroup.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, message, duration)
}

/**
 * Function to display a [Toast] message on the screen.
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, message, duration)
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return requireContext().toast(message, duration)
}

/**
 * Function to display refresh icon.
 */
fun SwipeRefreshLayout.inProgress() {
    isRefreshing = true
}

/**
 * Function to hide refresh icon.
 */
fun SwipeRefreshLayout.progressComplete() {
    isRefreshing = false
}

/**
 * Function to change the visibility of the the [View] to [View.VISIBLE]
 */
fun View.visible() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

/**
 * Function to change the visibility of the the [View] to [View.INVISIBLE]
 */
fun View.invisible() {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}

/**
 * Function to change the visibility of the the [View] to [View.GONE]
 */
fun View.gone() {
    if (visibility != View.GONE) visibility = View.GONE
}