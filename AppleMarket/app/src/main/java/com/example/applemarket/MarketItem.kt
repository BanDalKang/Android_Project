package com.example.applemarket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketItem(
    val itemImage: Int,
    val itemTitle: String,
    val itemContent: String,
    val itemPrice: String,
    val userName: String,
    val userAddress: String,
    val itemChatCount: Int,
    var itemLikeCount: Int,
    var isLiked: Boolean
) : Parcelable