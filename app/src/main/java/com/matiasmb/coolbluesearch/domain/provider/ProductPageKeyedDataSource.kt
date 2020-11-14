package com.matiasmb.coolbluesearch.domain.provider

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.matiasmb.coolbluesearch.data.model.Product
import com.matiasmb.coolbluesearch.data.networking.ItemsApiService
import com.matiasmb.coolbluesearch.presentation.model.ItemView
import com.matiasmb.coolbluesearch.presentation.model.TransactionState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

class ProductPageKeyedDataSource(
    private val apiService: ItemsApiService,
    private val query: String
) : PageKeyedDataSource<Int, ItemView>() {

    val transactionState = MutableLiveData<TransactionState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ItemView>
    ) {
        runBlocking {
            transactionState.postValue(TransactionState.Running)
            try {
                apiService.getProductsByQuery(query, DEFAULT_FIRST_PAGE).collect {
                    callback.onResult(transformResult(it.products), null, DEFAULT_FIRST_PAGE + 1)
                    transactionState.postValue(TransactionState.Success)
                }
            } catch (exception: Exception) {
                transactionState.postValue(TransactionState.Fail)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ItemView>) {
        // no - op
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ItemView>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        if (currentPage <= MAX_PAGES) {
            transactionState.postValue(TransactionState.Running)
            runBlocking {
                try {
                    apiService.getProductsByQuery(query, currentPage).collect {
                        callback.onResult(transformResult(it.products), nextPage)
                        transactionState.postValue(TransactionState.Success)
                    }
                } catch (exception: Exception) {
                    transactionState.postValue(TransactionState.Fail)
                }
            }
        }
    }

    private fun transformResult(productList: List<Product>): List<ItemView> {
        return productList.map { product ->
            ItemView.Product(
                product.productId,
                product.productName,
                product.productImage,
                product.salesPriceIncVat,
                product.availabilityState,
                product.reviewInformation.reviewSummary.reviewAverage,
                product.reviewInformation.reviewSummary.reviewCount,
                product.nextDayDelivery,
                product.USPs
            )
        }
    }

    companion object {
        const val DEFAULT_FIRST_PAGE = 1
        const val MAX_PAGES = 3
    }

}