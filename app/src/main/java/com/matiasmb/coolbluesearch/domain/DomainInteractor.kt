package com.matiasmb.coolbluesearch.domain

import com.matiasmb.coolbluesearch.data.model.Resource
import com.matiasmb.coolbluesearch.presentation.model.ItemView
import kotlinx.coroutines.flow.Flow

interface DomainInteractor {

    suspend fun performSearch(query: String): Flow<Resource<ArrayList<ItemView>>>
}