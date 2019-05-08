package com.sotwtm.support.fragment.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 */
abstract class AppHelpfulDataBindingDialogFragment<DataBindingClass : ViewDataBinding> :
    AppHelpfulDialogFragment() {

    @Volatile
    var dataBinding: DataBindingClass? = null
        private set


    /**
     * Initialize data binding. It will be called on data binding created.
     * @param dataBinding The data binding object bound with this fragment's view.
     * @param savedInstanceState The saved instance state of this fragment if any.
     * */
    abstract fun initDataBinding(dataBinding: DataBindingClass, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        layoutId?.let {
            dataBinding?.unbind()
            dataBinding = DataBindingUtil.inflate(inflater, it, container, false)

            dataBinding?.let {
                it.lifecycleOwner = this
                it.root
            } ?: inflater.inflate(it, container, false)
        } ?: super.onCreateView(inflater, container, savedInstanceState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding?.let { initDataBinding(it, savedInstanceState) }
    }

    override fun onDestroy() {
        super.onDestroy()

        dataBinding?.unbind()
        dataBinding = null
    }

    override fun createContentView(
        context: Context,
        @LayoutRes layoutId: Int
    ): View? {
        val inflater = LayoutInflater.from(context)
        dataBinding = DataBindingUtil.inflate(inflater, layoutId, null, false)
        return dataBinding?.root ?: inflater.inflate(layoutId, null, false)
    }
}
