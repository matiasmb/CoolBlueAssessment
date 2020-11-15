package com.matiasmb.coolblue.search.presentation.model

data class ItemView(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val stock: Int,
    val reviewAverage: Double,
    val reviewCount: Int,
    val nextDayDeliveryFlag: Boolean,
    val extraInfo: List<String>,
    val choiceTitle: String? = "",
    val promoItem: PromoItem? = null
)