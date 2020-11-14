package com.matiasmb.coolbluesearch.data.networking

import com.matiasmb.coolbluesearch.data.model.Response
import kotlinx.coroutines.flow.Flow

interface ItemsApiService {

    suspend fun getProductsByQuery(query: String, currentPage: Int): Flow<Response>
}