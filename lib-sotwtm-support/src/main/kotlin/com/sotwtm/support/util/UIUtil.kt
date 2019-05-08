package com.sotwtm.support.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sotwtm.support.R
import com.sotwtm.util.Log


/**
 * UI Utils for handling UI actions or calculation.
 * @author sheungon
 */

/**
 * Convert view pager ID to with ID to fragment tag.
 * @param pageId The page ID of the fragment in view pager
 * @return The fragment tag of the fragment at position in a view pager.
 */
fun Int.toFragmentTag(pageId: Long): String = "android:switcher:$this:$pageId"

/**
 * Get the Boolean value of a BoolRes
 * @return The resource boolean value
 * */
fun Int.boolResValue(context: Context): Boolean = context.resources.getBoolean(this)

/**
 * Get the color value of a ColorRes
 * @return The resource color value
 * */
@ColorInt
fun Int.colorResValue(context: Context): Int = ContextCompat.getColor(context, this)

/**
 * Get the string value of a StringRes
 * @return The resource string value
 * */
fun Int.stringResValue(context: Context): String = context.getString(this)

/**
 * Create a [Snackbar] for a [View]
 * @param message The message of the [Snackbar]
 * @param duration See [SnackbarDuration]
 * @param actionTextColorRes The action text color res
 * @param textColorRes The text color res
 * @param snackbarBackgroundColorRes The background color res of the snackbar
 * @return [Snackbar] from input [View]
 * */
fun View.createSnackbar(
    message: String,
    @SnackbarDuration duration: Int,
    @ColorRes actionTextColorRes: Int = R.color.snackbar_action_text,
    @ColorRes textColorRes: Int = R.color.snackbar_text,
    @ColorRes snackbarBackgroundColorRes: Int = R.color.snackbar_bg
): Snackbar =
    Snackbar.make(this, message, duration)
        .setActionTextColor(actionTextColorRes.colorResValue(context))
        .apply {
            val snackbarText =
                view.findViewById<TextView?>(com.google.android.material.R.id.snackbar_text)
            snackbarText?.setTextColor(textColorRes.colorResValue(context))

            view.setBackgroundColor(snackbarBackgroundColorRes.colorResValue(context))
        }

/**
 * Convert DP to pixel
 * @param context The context used for the conversion
 * */
fun Float.dpToPixel(context: Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

/**
 * Check if the app context is now in landscape mode.
 * @return true if the device orientation is landscape.
 * */
fun Context.isLandscapeNow(): Boolean =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * Hide the soft input keyboard from a given focus view
 * @param currentFocusView The focusing view to hide the keyboard.
 * */
fun Context.hideSoftKeyboard(currentFocusView: View): Context = apply {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
}

/**
 * Hide the soft input keyboard from an activity.
 * It will hide the keyboard from the current focus.
 * */
fun Activity.hideSoftKeyboard(): Activity = apply {
    currentFocus?.let { hideSoftKeyboard(it) }
        ?: Log.w("Cannot hide keyboard as no focus in the provided activity.")
}

/**
 * Hide the soft input keyboard from a fragment.
 * It will do nothing if the fragment has been removed from activity.
 * */
fun Fragment.hideSoftKeyboard(): Fragment = apply {
    activity?.hideSoftKeyboard()
}

/**
 * Hide Navigation bar now
 * @see hideNavigationAndStatusBar
 * */
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

/**
 * Hide status bar now
 * @see hideNavigationAndStatusBar
 * */
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

/**
 * Hide navigation bar and status bar
 * @see hideNavigationBar
 * @see hideStatusBar
 * */
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
