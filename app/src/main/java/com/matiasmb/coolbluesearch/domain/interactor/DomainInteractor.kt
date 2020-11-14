package com.matiasmb.coolbluesearch.domain.interactor

import com.matiasmb.coolbluesearch.presentation.model.ItemView
import com.matiasmb.coolbluesearch.presentation.model.Listing

interface DomainInteractor {

    fun performSearch(query: String): Listing<ItemView>
}