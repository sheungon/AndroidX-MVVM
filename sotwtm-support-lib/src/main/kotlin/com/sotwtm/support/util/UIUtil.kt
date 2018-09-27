package com.sotwtm.support.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.sotwtm.support.R
import com.sotwtm.util.Log


/**
 * UI Utils for handling UI actions or calculation.
 * @author John
 */
object UIUtil {

    /**@return The fragment tag of the fragment at position in a view pager.
     */
    fun getViewPagerFragmentTag(@IdRes viewPagerId: Int, pageId: Long): String {
        return "android:switcher:$viewPagerId:$pageId"
    }
}

fun View.createSnackbar(message: String,
                        @SnackbarDuration duration: Int): Snackbar =
        Snackbar.make(this, message, duration)
                .setActionTextColor(ContextCompat.getColor(context, R.color.snackbar_action_text))
                .apply {
                    val snackbarText = view.findViewById<TextView?>(android.support.design.R.id.snackbar_text)
                    snackbarText?.setTextColor(ContextCompat.getColor(context, R.color.snackbar_text))

                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_bg))
                }

fun Float.dpToPixel(context: Context) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

fun Context.isLandscapeNow(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.hideSoftKeyboard(currentFocusView: View): Context = apply {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
}

fun Activity.hideSoftKeyboard(): Activity = apply {
    currentFocus?.let { hideSoftKeyboard(it) }
            ?: Log.w("Cannot hide keyboard as no focus in the provided activity.")
}

fun Fragment.hideSoftKeyboard(): Fragment = apply {
    activity?.hideSoftKeyboard()
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun Activity.hideNavigationBar(): Activity = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        // Hide navigation bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION /*hide nav bar*/ or
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.SYSTEM_UI_FLAG_IMMERSIVE else 0)
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun Activity.hideStatusBar(): Activity = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        // Hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN /*hide nav bar*/ or
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.SYSTEM_UI_FLAG_IMMERSIVE else 0)
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun Activity.hideNavigationAndStatusBar(): Activity = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        // Full screen
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN /*hide nav bar*/ or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION /*hide nav bar*/ or
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.SYSTEM_UI_FLAG_IMMERSIVE else 0)
    }
}
