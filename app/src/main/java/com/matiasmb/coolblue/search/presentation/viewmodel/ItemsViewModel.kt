package com.matiasmb.coolblue.search.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.matiasmb.coolblue.search.domain.interactor.DomainInteractor
import com.matiasmb.coolblue.search.presentation.model.ItemView
import com.matiasmb.coolblue.search.presentation.model.TransactionState

class ItemsViewModel(private val domainInteractor: DomainInteractor) : ViewModel() {

    private val searchQuery = MutableLiveData<String>()
    private val stateSearchEvent = map(searchQuery) {
        domainInteractor.performSearch(it)

    }

    @VisibleForTesting(otherwise = PRIVATE)
    val products = switchMap(stateSearchEvent) { it.pagedList }

    @VisibleForTesting(otherwise = PRIVATE)
    val stateScreen = switchMap(stateSearchEvent) { it.transactionState }

    val liveDataMerger = MediatorLiveData<TransactionState<PagedList<ItemView>>>()

    init {
        liveDataMerger.addSource(products) {
            liveDataMerger.setValue(TransactionState.LoadData(it))
        }
        liveDataMerger.addSource(stateScreen) {
            liveDataMerger.setValue(it)
        }
    }

    fun fetchItems(query: String) {
        searchQuery.value = query
    }
}