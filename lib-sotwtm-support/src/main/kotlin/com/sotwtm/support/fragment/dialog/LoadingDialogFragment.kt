package com.sotwtm.support.fragment.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sotwtm.support.R
import com.sotwtm.support.databinding.DialogLoadingBinding


/**
 * A general loading dialog.
 *
 * By default, it is not cancelable.

 * Created by sheungon on 29/7/15.
 * @author sheungon
 */
class LoadingDialogFragment : AppHelpfulDataBindingDialogFragment<DialogLoadingBinding>() {

    init {
        isCancelable = false
    }

    override val layoutId: Int? = R.layout.dialog_loading
    override val daggerEnabled: Boolean = false
    override var viewModel: LoadingDialogFragmentViewModel? = null

    var loadingMsg: StringOrStringRes? = null
        set(value) {
            field = value
            viewModel?.loadingMsg?.sync(value)
        }

    private var dismissed: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.also {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return dialog
    }

    override fun initDataBinding(dataBinding: DialogLoadingBinding, savedInstanceState: Bundle?) {
        dataBinding.viewModel = activity?.let { activity ->
            LoadingDialogFragmentViewModel(activity).also {
                viewModel = it
                loadingMsg = loadingMsg
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // Sometime dismiss event called before loading dialog ready
        if (dismissed) {
            dismissAllowingStateLoss()
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        dismissed = false
        super.show(manager, tag)
    }

    override fun show(transaction: FragmentTransaction?, tag: String?): Int {
        dismissed = false
        return super.show(transaction, tag)
    }

    override fun showNow(manager: FragmentManager?, tag: String?) {
        dismissed = false
        super.showNow(manager, tag)
    }

    override fun dismissAllowingStateLoss() {
        dismissed = true
        super.dismissAllowingStateLoss()
    }

    override fun dismiss() {
        dismissed = true
        super.dismiss()
    }
}
