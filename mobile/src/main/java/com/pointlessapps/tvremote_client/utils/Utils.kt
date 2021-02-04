package com.pointlessapps.tvremote_client.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

object Utils {

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> getViewModel(
        modelClass: Class<T>,
        activity: AppCompatActivity,
        owner: ViewModelStoreOwner = activity,
        vararg args: Any
    ) = ViewModelProvider(
        owner,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                modelClass.constructors.first().newInstance(activity, *args) as T
        }
    ).get(modelClass)

    fun showKeyboard(context: Context, view: View) {
        view.post { view.requestFocus() }
        context.getSystemService(InputMethodManager::class.java)
            .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideKeyboard(context: Context, view: View) {
        context.getSystemService(InputMethodManager::class.java)
            .hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setOnKeyboardChangeVisibilityListener(
        view: View,
        onChangeVisibilityListener: (Boolean) -> Unit
    ) = view.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect().also { view.getWindowVisibleDisplayFrame(it) }
        onChangeVisibilityListener(view.rootView.height - (rect.bottom - rect.top) > 100)
    }
}