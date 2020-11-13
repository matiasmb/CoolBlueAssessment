package com.matiasmb.coolbluesearch.presentation.model

sealed class ItemView {
    class Product(
        val name: String,
        val imageUrl: String,
        val price: Double,
        val stock: Int,
        val reviewAverage: Double,
        val reviewCount: Int,
        val nextDayDeliveryFlag: Boolean,
        val extraInfo: List<String>
    ) : ItemView()
}