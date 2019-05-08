package com.sotwtm.support.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * A fragment contains helpful API.

 * @author sheungon
 */
abstract class AppHelpfulDataBindingFragment<DataBindingClass : ViewDataBinding> :
    AppHelpfulFragment() {

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
    ): View {

        dataBinding?.unbind()
        dataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        return dataBinding?.let {
            it.lifecycleOwner = this
            it.root
        } ?: inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataBinding?.let { initDataBinding(it, savedInstanceState) }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dataBinding?.unbind()
        dataBinding = null
    }

    /**
     * @return `true` if view is bound by ButterKnife. Otherwise, `false`
     * *
     */
    override val isViewBound: Boolean
        get() = super.isViewBound || dataBinding != null
}
