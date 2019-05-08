package com.sotwtm.support.util

import androidx.annotation.IntDef
import com.google.android.material.snackbar.Snackbar


/**
 * [Snackbar] duration constants.
 * @author sheungon
 */
@IntDef(Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class SnackbarDuration
