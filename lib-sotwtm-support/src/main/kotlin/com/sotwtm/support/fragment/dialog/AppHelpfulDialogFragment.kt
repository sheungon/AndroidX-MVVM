package com.sotwtm.support.fragment.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sotwtm.support.R
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.activity.IOverridePendingTransition
import com.sotwtm.util.Log
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 */
abstract class AppHelpfulDialogFragment : AppCompatDialogFragment(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var childFragmentInjector: Lazy<DispatchingAndroidInjector<Fragment>?>

    abstract val layoutId: Int?
    abstract val viewModel: AppHelpfulDialogFragmentViewModel?

    /**Indicate if dagger injection is enabled to this dialog fragment.*/
    open val daggerEnabled: Boolean = true

    override fun onAttach(context: Context) {
        try {
            if (daggerEnabled) AndroidSupportInjection.inject(this)
        } catch (e: Exception) {
            Log.e("If dagger in not needed, disable dagger by setting daggerEnabled.")
            throw e
        }
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel?.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        layoutId?.let {
            inflater.inflate(it, container, false)
        } ?: super.onCreateView(inflater, container, savedInstanceState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.onViewCreatedInternal(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        viewModel?.onStart()
    }

    override fun onResume() {
        super.onResume()

        viewModel?.onResumeInternal()
    }

    override fun onPause() {
        super.onPause()

        viewModel?.onPauseInternal()
    }

    override fun onStop() {
        super.onStop()

        viewModel?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel?.onDestroyViewInternal()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel?.onDestroy()
    }

    override fun startActivity(intent: Intent) {
        startActivity(intent, null)
    }

    override fun startActivity(intent: Intent, options: Bundle?) {
        startActivity(intent, true, options)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun startActivity(
        intent: Intent,
        overridePendingTransition: Boolean,
        options: Bundle? = null
    ) {
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
    fun startActivityForResult(
        intent: Intent,
        requestCode: Int,
        overridePendingTransition: Boolean,
        options: Bundle? = null
    ) {
        val activity = activity ?: return {
            Log.e("Cannot start activity as the fragment has been released.")
            Unit
        }.invoke()

        super.startActivityForResult(intent, requestCode, options)

        if (overridePendingTransition) {
            (activity as? IOverridePendingTransition)?.overridePendingTransitionForStartActivity()
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            val ft = (manager ?: return).beginTransaction()
            ft.add(this, tag)
            ft.commit()
        } catch (e: IllegalStateException) {
            Log.e("Exception on fragment dialog show", e)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? =
        childFragmentInjector.get()

    fun onEditorAction(actionId: Int): Boolean {

        when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                onOkClick()
                return true
            }
        }

        return false
    }

    protected open fun createContentView(
        context: Context,
        @LayoutRes layoutId: Int
    ): View? {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(layoutId, null, false)
    }

    internal fun showLoadingDialog() {
        (activity as? AppHelpfulActivity)?.showLoadingDialog()
    }

    internal fun showLoadingDialog(@StringRes msgRes: Int) {
        (activity as? AppHelpfulActivity)?.showLoadingDialog(msgRes)
    }

    internal fun showLoadingDialog(msg: String) {
        (activity as? AppHelpfulActivity)?.showLoadingDialog(msg)
    }

    internal fun dismissLoadingDialog() {
        (activity as? AppHelpfulActivity)?.dismissLoadingDialog()
    }

    protected fun showNothing() {
        setContentView(R.layout.empty)
    }

    /**
     * Override this method to do your action on "OK" clicked if you want to keep this dialog showing.<br></br>
     * You will also need to set [OnShowSetupOnOkClickListener] to your dialog on show.<br></br>
     * !!!!!Note, using this method, you shouldn't implement positive button's onclick listener!!!!!<br></br>
     */
    @UiThread
    protected open fun onOkClick() {
    }

    private fun setContentView(@LayoutRes layoutResId: Int) {

        val fragmentDialog = dialog ?: return

        // Make dialog content gone
        fragmentDialog.setContentView(
            LayoutInflater.from(context).inflate(
                layoutResId,
                null,
                false
            )
        )
        fragmentDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


    ///////////////////////////////////
    // Class and interface
    ///////////////////////////////////
    /**
     * Set this on show listener to you dialog to make dialog OK button on click response to [.onOkClick].
     * Using this, the dialog will NOT be dismissed on OK button click
     */
    protected class OnShowSetupOnOkClickListener(fragment: AppHelpfulDialogFragment) :
        DialogInterface.OnShowListener {

        private val fragmentRef: WeakReference<AppHelpfulDialogFragment> = WeakReference(fragment)

        override fun onShow(dialog: DialogInterface) {

            val fragment = fragmentRef.get() ?: return

            /*Set listener here to avoid dialog auto dismissed*/
            val button = (dialog as? AlertDialog)?.getButton(AlertDialog.BUTTON_POSITIVE)
            button?.setOnClickListener(MyOnOkClickListener(fragment))
        }
    }

    internal class MyOnOkClickListener(fragment: AppHelpfulDialogFragment) : View.OnClickListener {

        private val fragmentRef: WeakReference<AppHelpfulDialogFragment> = WeakReference(fragment)

        override fun onClick(view: View) {

            val fragment = fragmentRef.get() ?: return

            fragment.onOkClick()
        }
    }
}
