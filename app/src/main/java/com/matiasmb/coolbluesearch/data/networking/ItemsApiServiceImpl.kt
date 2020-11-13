package com.matiasmb.coolbluesearch.data.networking

import com.matiasmb.coolbluesearch.data.model.Resource
import com.matiasmb.coolbluesearch.data.model.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class ItemsApiServiceImpl(
    private val apiClient: ItemsApiClient
) : ItemsApiService {

    override suspend fun getReposByUsername(query: String) : Flow<Resource<Response>> {
        return flow {
            apiClient.searchProducts(query, 1).takeIf {
                it.products.isNotEmpty()
            }?.run {
                emit(
                    Resource.Success(this)
                )
            } ?: run {
                emit(
                    Resource.Failure
                )
            }
        }.catch {
            Resource.Failure
        }
    }
}