package com.matiasmb.coolblue.search.domain.interactor

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.matiasmb.coolblue.search.data.networking.ItemsApiService
import com.matiasmb.coolblue.search.domain.provider.ProductDataSourceFactory
import com.matiasmb.coolblue.search.presentation.model.ItemView
import com.matiasmb.coolblue.search.presentation.model.Listing
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class DomainInteractorImpl(
    private val itemsApiService: ItemsApiService
) : DomainInteractor {

    override fun performSearch(query: String): Listing<ItemView> {
        val factory = getProductDataSourceFactory(query)
        val config = getPagedListConfig()
        return Listing(
            pagedList = LivePagedListBuilder(factory, config).build(),
            transactionState = switchMap(factory.source) { it.transactionState }
        )
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getProductDataSourceFactory(searchQuery: String): ProductDataSourceFactory {
        return ProductDataSourceFactory(searchQuery, itemsApiService)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()
    }

    companion object {
        const val PAGE_SIZE = 24
    }
}
