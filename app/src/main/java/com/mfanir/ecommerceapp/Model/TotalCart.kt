package com.mfanir.ecommerceapp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TotalCart (
    var product: String ?="",
    var price: String ?=""
): Parcelable