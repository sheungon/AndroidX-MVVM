package com.sotwtm.support.fragment.dialog

import android.app.Activity

/**
 * View Model of [LoadingDialogFragment]

 * @author sheungon
 */
class LoadingDialogFragmentViewModel(activity: Activity) :
    AppHelpfulDialogFragmentViewModel(activity.application) {

    val loadingMsg: StringOrStringRes = StringOrStringRes(activity)
}