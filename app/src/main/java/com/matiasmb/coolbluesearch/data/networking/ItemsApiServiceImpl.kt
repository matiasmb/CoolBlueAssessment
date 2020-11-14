package com.matiasmb.coolbluesearch.data.networking

import com.matiasmb.coolbluesearch.data.model.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class ItemsApiServiceImpl(
    private val apiClient: ItemsApiClient
) : ItemsApiService {

    override suspend fun getProductsByQuery(query: String, currentPage: Int): Flow<Response> {
        return flow {
            apiClient.searchProducts(query, currentPage).run {
                emit(this)
            }
        }
    }
}