package com.dvm.order.ordering.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class OrderingFields(
    val entrance: String = "",
    val floor: String = "",
    val apartment: String = "",
    val intercom: String = "",
    val comment: String = "",
) : Parcelable