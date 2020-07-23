package com.mfanir.ecommerceapp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart (
    var date: String? = "",
    var discount: String? = "",
    var image: String? = "",
    var pid: String? = "",
    var pname: String? = "",
    var price: String? = "",
    var quantity: String? = "",
    var seller: String? = "",
    var time: String? = ""
): Parcelable



