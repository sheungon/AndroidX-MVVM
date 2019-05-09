package com.sotwtm.example.sub.fragment

import com.sotwtm.example.R
import com.sotwtm.support.fragment.AppHelpfulFragment
import javax.inject.Inject

class NoDataBindingFragment : AppHelpfulFragment() {
    override val layoutResId: Int = R.layout.fragment_no_data_binding
    @Inject
    override lateinit var viewModel: NoDataBindingFragmentViewModel
}