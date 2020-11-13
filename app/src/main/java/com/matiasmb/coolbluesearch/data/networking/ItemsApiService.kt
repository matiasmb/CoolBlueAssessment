package com.matiasmb.coolbluesearch.data.networking

import com.matiasmb.coolbluesearch.data.model.Resource
import com.matiasmb.coolbluesearch.data.model.Response
import kotlinx.coroutines.flow.Flow

interface ItemsApiService {

    suspend fun getReposByUsername(query: String): Flow<Resource<Response>>
}