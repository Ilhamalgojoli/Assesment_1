package com.ilhamalgojali0081.assesment_1.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val quantity: String,
    val stokInDate: String,
    @DrawableRes val icon: Int
):Parcelable
