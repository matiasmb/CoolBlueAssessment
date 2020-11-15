package com.matiasmb.coolblue

import com.matiasmb.coolblue.search.data.model.Product
import com.matiasmb.coolblue.search.data.model.PromoIcon
import com.matiasmb.coolblue.search.data.model.Response
import com.matiasmb.coolblue.search.data.model.Review
import com.matiasmb.coolblue.search.data.model.ReviewSummary
import com.matiasmb.coolblue.search.presentation.model.ItemView
import com.matiasmb.coolblue.search.presentation.model.PromoItem
import kotlinx.coroutines.flow.flow

object TestData {

    private val product = Product(
        productId = 1,
        productName = "iphone",
        availabilityState = 3,
        salesPriceIncVat = 235.21,
        productImage = "https://image.coolblue.nl/300x750/products/1009539",
        nextDayDelivery = true,
        USPs = listOf("small", "iphone x", "good camera"),
        reviewInformation = Review(emptyList<Any>(), ReviewSummary(7.5, 3123)),
        promoIcon = PromoIcon("textTest", "typeTest"),
        coolbluesChoiceInformationTitle = "testTitle"
    )
    val dataProductsResponseEmpty = Response(
        emptyList(),
        1,
        0,
        1
    )
    val dataProductsResponse = Response(
        listOf(product),
        1,
        24,
        3
    )

    val serviceSuccessResponse = flow { emit(dataProductsResponse) }

    val itemViewList = listOf(
        ItemView(
            id = 1,
            name = "iphone",
            imageUrl = "https://image.coolblue.nl/300x750/products/1009539",
            price = 235.21,
            stock = 3,
            reviewAverage = 7.5,
            reviewCount = 3123,
            nextDayDeliveryFlag = true,
            extraInfo = listOf("small", "iphone x", "good camera"),
            choiceTitle = "testTitle",
            promoItem = PromoItem.CoolBluesChoice("textTest")
        )
    )
}