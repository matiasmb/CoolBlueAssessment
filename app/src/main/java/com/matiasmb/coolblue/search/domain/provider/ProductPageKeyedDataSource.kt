package com.matiasmb.coolblue.search.domain.provider

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.matiasmb.coolblue.search.data.model.Product
import com.matiasmb.coolblue.search.data.model.PromoIcon
import com.matiasmb.coolblue.search.data.networking.ItemsApiService
import com.matiasmb.coolblue.search.presentation.model.ItemView
import com.matiasmb.coolblue.search.presentation.model.PromoItem
import com.matiasmb.coolblue.search.presentation.model.TransactionState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

class ProductPageKeyedDataSource(
    private val apiService: ItemsApiService,
    private val query: String
) : PageKeyedDataSource<Int, ItemView>() {

    val transactionState =
        MutableLiveData<TransactionState<PagedList<ItemView>>>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ItemView>
    ) {
        runBlocking {
            transactionState.postValue(TransactionState.Running)
            try {
                apiService.getProductsByQuery(query, DEFAULT_FIRST_PAGE).collect {
                    callback.onResult(transformResult(it.products), null, DEFAULT_FIRST_PAGE + 1)
                    transactionState.postValue(TransactionState.EndLoadData)
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
                        transactionState.postValue(TransactionState.EndLoadData)
                    }
                } catch (exception: Exception) {
                    transactionState.postValue(TransactionState.Fail)
                }
            }
        }
    }

    private fun transformResult(productList: List<Product>): List<ItemView> {
        return productList.map { product ->
            ItemView(
                product.productId,
                product.productName,
                product.productImage,
                product.salesPriceIncVat,
                product.availabilityState,
                product.reviewInformation.reviewSummary.reviewAverage,
                product.reviewInformation.reviewSummary.reviewCount,
                product.nextDayDelivery,
                product.USPs,
                product.coolbluesChoiceInformationTitle,
                getPromoItem(product.promoIcon)
            )
        }
    }

    private fun getPromoItem(promoIcon: PromoIcon?): PromoItem? {
        return when (promoIcon?.type) {
            COOLBLUES_CHOICE -> PromoItem.CoolBluesChoice(promoIcon.text)
            ACTION_PRICE -> PromoItem.ActionPrice(promoIcon.text)
            else -> null
        }
    }

    companion object {
        const val DEFAULT_FIRST_PAGE = 1
        const val MAX_PAGES = 3
        const val COOLBLUES_CHOICE = "coolblues-choice"
        const val ACTION_PRICE = "action-price"
    }

}