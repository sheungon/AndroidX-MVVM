package com.sotwtm.example.sub.fragment.sub

import android.os.Bundle
import com.sotwtm.example.R
import com.sotwtm.example.databinding.FragmentSubcomponentBinding
import com.sotwtm.support.fragment.AppHelpfulDataBindingFragment
import javax.inject.Inject

/**
 *
 * */
class SubcomponentFragment : AppHelpfulDataBindingFragment<FragmentSubcomponentBinding>() {

    override val layoutResId: Int = R.layout.fragment_subcomponent
    @Inject
    override lateinit var viewModel: SubcomponentFragmentViewModel

    override fun initDataBinding(dataBinding: FragmentSubcomponentBinding, savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }
}