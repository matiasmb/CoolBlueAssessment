package com.matiasmb.coolblue.search.domain.provider

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.matiasmb.coolblue.search.data.networking.ItemsApiService
import com.matiasmb.coolblue.search.presentation.model.ItemView

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class ProductDataSourceFactory(
    private val searchQuery: String,
    private val itemsApiService: ItemsApiService
) : DataSource.Factory<Int, ItemView>() {

    val source = MutableLiveData<ProductPageKeyedDataSource>()

    override fun create(): DataSource<Int, ItemView> {
        val source = ProductPageKeyedDataSource(itemsApiService, searchQuery)
        this.source.postValue(source)
        return source
    }
}