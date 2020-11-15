package com.matiasmb.coolblue.search.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.matiasmb.coolblue.search.domain.interactor.DomainInteractor

class ItemsViewModel(private val domainInteractor: DomainInteractor) : ViewModel() {

    private val searchQuery = MutableLiveData<String>()
    private val stateSearchEvent = map(searchQuery) {
        domainInteractor.performSearch(it)

    }
    val products = switchMap(stateSearchEvent) { it.pagedList }
    val stateScreen = switchMap(stateSearchEvent) { it.transactionState }

    fun fetchItems(query: String) {
        searchQuery.value = query
    }
}