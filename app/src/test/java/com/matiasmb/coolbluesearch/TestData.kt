package com.matiasmb.coolbluesearch

import com.matiasmb.coolbluesearch.data.model.Product
import com.matiasmb.coolbluesearch.data.model.PromoIcon
import com.matiasmb.coolbluesearch.data.model.Response
import com.matiasmb.coolbluesearch.data.model.Review
import com.matiasmb.coolbluesearch.data.model.ReviewSummary
import com.matiasmb.coolbluesearch.presentation.model.ItemView
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
    val dataRepoResponseEmpty = Response(
        emptyList(),
        1,
        0,
        1
    )
    val dataRepoResponse = Response(
        listOf(product),
        1,
        24,
        3
    )

    val serviceSuccessResponse = flow { emit(dataRepoResponse) }

    val itemViewList = listOf(
        ItemView.Product(
            id = 1,
            name = "iphone",
            imageUrl = "https://image.coolblue.nl/300x750/products/1009539",
            price = 235.21,
            stock = 3,
            reviewAverage = 7.5,
            reviewCount = 3123,
            nextDayDeliveryFlag = true,
            extraInfo = listOf("small", "iphone x", "good camera")
        )
    )
}