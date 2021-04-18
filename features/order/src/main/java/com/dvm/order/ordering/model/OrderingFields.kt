package com.dvm.order.ordering.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderingFields(
    val address: String = "",
    val entrance: String = "",
    val floor: String = "",
    val apartment: String = "",
    val intercom: String = "",
    val comment: String = "",
) : Parcelable