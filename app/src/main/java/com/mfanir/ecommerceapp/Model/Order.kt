package com.mfanir.ecommerceapp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order (
    var address: String? = "",
    var date: String? = "",
    var name: String? = "",
    var phone: String? = "",
    var time: String? = "",
): Parcelable