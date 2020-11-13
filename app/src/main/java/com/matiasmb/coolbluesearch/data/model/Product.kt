package com.matiasmb.coolbluesearch.data.model

data class Product(
    val productId: Int,
    val productName: String,
    val availabilityState: Int,
    val salesPriceIncVat: Double,
    val productImage: String,
    val nextDayDelivery: Boolean,
    val USPs: List<String>,
    val reviewInformation: Review,
    val promoIcon: PromoIcon?,
    val coolbluesChoiceInformationTitle: String?
)