package com.matiasmb.coolblue.search.domain.interactor

import com.matiasmb.coolblue.search.presentation.model.ItemView
import com.matiasmb.coolblue.search.presentation.model.Listing

interface DomainInteractor {

    fun performSearch(query: String): Listing<ItemView>
}