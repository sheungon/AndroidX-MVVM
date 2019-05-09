package com.sotwtm.example.sub.fragment

import android.os.Bundle
import com.sotwtm.example.R
import com.sotwtm.example.databinding.FragmentSimpleDaggerBinding
import com.sotwtm.support.fragment.AppHelpfulDataBindingFragment
import javax.inject.Inject

/**
 * A simple example showing how to inject with dagger by SotwtmSupportLib
 * @author sheungon
 * */
class SimpleDaggerFragment : AppHelpfulDataBindingFragment<FragmentSimpleDaggerBinding>() {

    override val layoutResId: Int = R.layout.fragment_simple_dagger
    @Inject
    override lateinit var viewModel: SimpleDaggerFragmentViewModel

    override fun initDataBinding(dataBinding: FragmentSimpleDaggerBinding, savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }
}
