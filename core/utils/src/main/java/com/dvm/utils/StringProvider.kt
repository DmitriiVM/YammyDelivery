package com.dvm.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProvider @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun getString(resId: Int): String = context.getString(resId)
}