package com.matiasmb.coolblue.search.presentation.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Data class necessary to connect the presentation and provide information about the events related to requests
 */
data class Listing<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
    // represents the network request status to show to the user
    val transactionState: LiveData<TransactionState<PagedList<ItemView>>>
)