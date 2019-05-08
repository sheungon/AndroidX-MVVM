package com.sotwtm.support.dialog

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

 * Created by johntsai on 29/7/15.
 * @author sheungon
 */
class LoadingDialogFragment : AppHelpfulDataBindingDialogFragment<DialogLoadingBinding>() {

    init {
        isCancelable = CANCELABLE
    }

    override val layoutId: Int? = R.layout.dialog_loading
    override val dataBinder: AppHelpfulDialogFragmentDataBinder? = null
    override val daggerEnabled: Boolean = false

    var loadingMsg: StringOrStringRes? = null
        set(value) {
            field = value
            dataBinding?.loadingMsg = value
        }

    private var dismissed: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return dialog
    }

    override fun initDataBinding(dataBinding: DialogLoadingBinding, savedInstanceState: Bundle?) {
        dataBinding.loadingMsg = loadingMsg
    }

    override fun onResume() {
        super.onResume()

        // Sometime dismiss event called before loading dialog ready
        if (dismissed) {
            dismiss()
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

    companion object {
        private const val CANCELABLE = false
    }
}
