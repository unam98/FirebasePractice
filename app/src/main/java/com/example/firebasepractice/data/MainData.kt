package com.example.firebasepractice.data


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainData(
    val imageUrl: String,
    val placeName: String,
) : Parcelable