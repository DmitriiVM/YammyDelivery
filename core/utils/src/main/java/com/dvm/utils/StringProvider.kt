package com.dvm.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProvider @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun getString(resId: Int): String = context.getString(resId)

    fun getQuantityString(resId: Int, quantity: Int): String =
        context.resources.getQuantityString(resId, quantity, quantity)

    fun getString(resId: Int, vararg args: Any) =
        String.format(context.getString(resId, *args))
}