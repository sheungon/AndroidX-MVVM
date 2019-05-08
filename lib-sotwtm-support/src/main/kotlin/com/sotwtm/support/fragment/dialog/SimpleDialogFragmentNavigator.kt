package com.sotwtm.support.fragment.dialog

import com.sotwtm.support.base.BaseNavigator
import com.sotwtm.support.scope.FragmentScope
import javax.inject.Inject

@FragmentScope
class SimpleDialogFragmentNavigator
@Inject
constructor(fragment: AppHelpfulDialogFragment) : BaseNavigator(fragment)
