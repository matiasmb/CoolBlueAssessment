package com.matiasmb.coolbluesearch.domain

import com.matiasmb.coolbluesearch.data.model.Resource
import com.matiasmb.coolbluesearch.data.networking.ItemsApiService
import com.matiasmb.coolbluesearch.presentation.model.ItemView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class DomainInteractorImpl(
    private val itemsApiService: ItemsApiService
) : DomainInteractor {

    override suspend fun performSearch(query: String): Flow<Resource<ArrayList<ItemView>>> {
        return flow {
            itemsApiService.getReposByUsername(query).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        val responseListView: ArrayList<ItemView> = ArrayList()
                        response.data.products.forEach { product ->
                            responseListView.add(
                                ItemView.Product(
                                    product.productName,
                                    product.productImage,
                                    product.salesPriceIncVat,
                                    product.availabilityState,
                                    product.reviewInformation.reviewSummary.reviewAverage,
                                    product.reviewInformation.reviewSummary.reviewCount,
                                    product.nextDayDelivery,
                                    product.USPs
                                )
                            )
                        }
                        emit(Resource.Success(responseListView))
                    }
                    is Resource.Failure -> emit(Resource.Failure)
                }
            }
        }
    }
}
