package com.matiasmb.coolblue.search.data.networking

import com.matiasmb.coolblue.search.data.model.Response
import kotlinx.coroutines.flow.Flow

interface ItemsApiService {

    suspend fun getProductsByQuery(query: String, currentPage: Int): Flow<Response>
}