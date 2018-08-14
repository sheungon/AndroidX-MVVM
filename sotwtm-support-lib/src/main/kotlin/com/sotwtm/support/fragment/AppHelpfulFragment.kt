package com.sotwtm.support.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sotwtm.support.R
import com.sotwtm.support.SotwtmSupportLib
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.activity.IOverridePendingTransition
import com.sotwtm.support.util.SnackbarUtil
import com.sotwtm.support.util.UIUtil
import com.sotwtm.util.Log
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * A fragment contains helpful API.

 * Created by sheun on 10/11/2015.
 * @author John
 */
abstract class AppHelpfulFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var childFragmentInjector: Lazy<DispatchingAndroidInjector<Fragment>?>

    /**
     * The layout ID for this fragment
     */
    @get:LayoutRes
    abstract val layoutResId: Int

    /**
     * The Enter screen animation to override on start activity
     * @return 0 means no animation the activity animation
     * *
     */
    open val startEnterAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_in_from_right

    /**
     * The Exit screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    open val startExitAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_out_to_left

    /**
     * The Enter screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    open val finishEnterAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_in_from_left

    /**
     * The Exit screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    open val finishExitAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_out_to_right

    abstract val dataBinder: AppHelpfulFragmentDataBinder?

    override fun onAttach(context: Context?) {
        try {
            AndroidSupportInjection.inject(this)
        } catch (e: Exception) {
            if (SotwtmSupportLib.enableDaggerErrorLog) {
                Log.e("Disable dagger error log by SotwtmSupportLib.enableDaggerErrorLog", e)
            }
        }
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinder?.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutResId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinder?.onViewCreatedInternal(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        dataBinder?.onStart()
    }

    override fun onResume() {
        super.onResume()

        dataBinder?.onResumeInternal()
    }

    override fun onPause() {
        super.onPause()

        dataBinder?.onPauseInternal()
    }

    override fun onStop() {
        super.onStop()

        dataBinder?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dataBinder?.onDestroyViewInternal()
    }

    override fun onDestroy() {
        super.onDestroy()

        dataBinder?.onDestroy()
    }

    override fun startActivity(intent: Intent) {
        startActivity(intent, null)
    }

    override fun startActivity(intent: Intent, options: Bundle?) {
        startActivity(intent, true, options)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun startActivity(intent: Intent,
                      overridePendingTransition: Boolean,
                      options: Bundle? = null) {
        val activity = activity ?: return {
            Log.e("Cannot start activity as the fragment has been released.")
            Unit
        }.invoke()

        super.startActivity(intent, options)

        if (overridePendingTransition) {
            (activity as? IOverridePendingTransition)?.overridePendingTransitionForStartActivity()
        }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode, null)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        startActivityForResult(intent, requestCode, true, options)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun startActivityForResult(intent: Intent,
                               requestCode: Int,
                               overridePendingTransition: Boolean,
                               options: Bundle? = null) {
        val activity = activity ?: return {
            Log.e("Cannot start activity as the fragment has been released.")
            Unit
        }.invoke()

        super.startActivityForResult(intent, requestCode, options)

        if (overridePendingTransition) {
            (activity as? IOverridePendingTransition)?.overridePendingTransitionForStartActivity()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = childFragmentInjector.get()

    /**
     * @return `true` if view is bound by ButterKnife. Otherwise, `false`
     * *
     */
    open val isViewBound: Boolean
        get() = isResumed

    fun showLoadingDialog() {

        val activity = activity
        if (activity != null) {
            (activity as? AppHelpfulActivity)?.showLoadingDialog()
                    ?: Log.wtf("This method can only work with parent is AppHelpfulActivity")
        } else {
            Log.v("Fragment released")
        }
    }

    fun showLoadingDialog(@StringRes msgRes: Int?) {

        val activity = activity
        if (activity != null) {
            (activity as? AppHelpfulActivity)?.showLoadingDialog(msgRes)
                    ?: Log.wtf("This method can only work with parent is AppHelpfulActivity")
        } else {
            Log.v("Fragment released")
        }
    }

    fun dismissLoadingDialog() {

        val activity = activity
        if (activity != null) {
            (activity as? AppHelpfulActivity)?.dismissLoadingDialog()
                    ?: Log.wtf("This method can only work with parent is AppHelpfulActivity")
        } else {
            Log.v("Fragment released")
        }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(@StringRes messageRes: Int,
                     @SnackbarUtil.SnackbarDuration duration: Int) {

        val activity = activity
        (activity as? AppHelpfulActivity)?.showSnackBar(messageRes, duration)
                ?: if (activity != null) {
                    showSnackBar(activity.getString(messageRes), duration)
                } else {
                    Log.e("Fragment is not attached! message lost : $messageRes")
                }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(message: String,
                     @SnackbarUtil.SnackbarDuration duration: Int) {

        if (!isViewBound) {
            return
        }

        val activity = activity
        (activity as? AppHelpfulActivity)?.showSnackBar(message, duration)
                ?: if (activity != null) {

                    val rootView = view
                    if (rootView == null) {
                        Log.w("Cannot get root view.")
                        return
                    }
                    activity.runOnUiThread {
                        dismissLoadingDialog()
                        UIUtil.hideSoftKeyboard(activity)

                        val snackbar = SnackbarUtil.create(activity, rootView, message, duration)
                        snackbar.show()
                    }

                } else {
                    Log.e("Fragment is not attached! message lost : $message")
                }
    }

    fun createSnackBarWithRootView(@StringRes messageRes: Int,
                                   @SnackbarUtil.SnackbarDuration duration: Int): Snackbar? {
        val activity = activity
        return (activity as? AppHelpfulActivity)?.createSnackBarWithRootView(messageRes, duration)
                ?: if (activity != null) createSnackBarWithRootView(activity.getString(messageRes), duration)
                else {
                    Log.e("Fragment is not attached! message lost : $messageRes")
                    null
                }
    }

    fun createSnackBarWithRootView(message: String,
                                   @SnackbarUtil.SnackbarDuration duration: Int): Snackbar? {
        return (activity as? AppHelpfulActivity)?.createSnackBarWithRootView(message, duration)
                ?: {
                    Log.e("Fragment is not attached! message lost : $message")
                    null
                }.invoke()
    }
}