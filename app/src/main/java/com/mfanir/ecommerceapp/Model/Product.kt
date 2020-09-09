package com.mfanir.ecommerceapp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
        var category: String? = "",
        var date: String? = "",
        var description: String? = "",
        var image: String? = "",
        var pid: String? = "",
        var pname: String? = "",
        var price: String? = "",
        var seller: String? = "",
        var time: String? = "",

) : Parcelable