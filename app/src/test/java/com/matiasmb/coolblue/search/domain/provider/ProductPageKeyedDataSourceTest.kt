package com.matiasmb.coolblue.search.domain.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PageKeyedDataSource
import com.matiasmb.coolblue.CoroutinesRule
import com.matiasmb.coolblue.TestData.serviceSuccessResponse
import com.matiasmb.coolblue.search.data.networking.ItemsApiService
import com.matiasmb.coolblue.getOrAwaitValue
import com.matiasmb.coolblue.search.presentation.model.TransactionState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class ProductPageKeyedDataSourceTest {

    @get:Rule
    var coroutinesRule = CoroutinesRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `loadInitial SHOULD send a TransactionState Success WHEN itemsApiService return a successful response`() {

        runBlocking {
            launch(Dispatchers.Main) {
                // GIVEN
                val itemsApiService = mock<ItemsApiService> {
                    onBlocking {
                        getProductsByQuery(
                            anyString(),
                            anyInt()
                        )
                    } doReturn serviceSuccessResponse
                }
                val productPageKeyedDataSource =
                    ProductPageKeyedDataSource(itemsApiService, "iphone")

                // WHEN
                productPageKeyedDataSource.loadInitial(
                    PageKeyedDataSource.LoadInitialParams(
                        24,
                        false
                    ), mock()
                )

                //THEN
                assertTrue(productPageKeyedDataSource.transactionState.getOrAwaitValue() is TransactionState.Success)
            }
        }
    }

    @Test
    fun `loadInitial SHOULD send a TransactionState Fail WHEN itemsApiService throws an Exception`() {
        runBlocking {
            launch(Dispatchers.Main) {
                // GIVEN
                val itemsApiService = mock<ItemsApiService> {
                    onBlocking {
                        getProductsByQuery(
                            anyString(),
                            anyInt()
                        )
                    } doThrow RuntimeException("HttpException")
                }

                val productPageKeyedDataSource =
                    ProductPageKeyedDataSource(itemsApiService, "iphone")

                // WHEN
                productPageKeyedDataSource.loadInitial(
                    PageKeyedDataSource.LoadInitialParams(
                        24,
                        false
                    ), mock()
                )

                // THEN
                assertTrue(productPageKeyedDataSource.transactionState.getOrAwaitValue() is TransactionState.Fail)
            }
        }
    }

    @Test
    fun `loadAfter SHOULD send a TransactionState Success WHEN itemsApiService return a successful response`() {
        runBlocking {
            launch(Dispatchers.Main) {
                // GIVEN
                val itemsApiService = mock<ItemsApiService> {
                    onBlocking {
                        getProductsByQuery(
                            anyString(),
                            anyInt()
                        )
                    } doReturn serviceSuccessResponse
                }
                val productPageKeyedDataSource =
                    ProductPageKeyedDataSource(itemsApiService, "iphone")

                // WHEN
                productPageKeyedDataSource.loadAfter(
                    PageKeyedDataSource.LoadParams(
                        2,
                        24
                    ), mock()
                )

                //THEN
                assertTrue(productPageKeyedDataSource.transactionState.getOrAwaitValue() is TransactionState.Success)
            }
        }
    }

    @Test
    fun `loadAfter SHOULD send a TransactionState Fail WHEN itemsApiService throws an Exception`() {
        runBlocking {
            launch(Dispatchers.Main) {
                // GIVEN
                val itemsApiService = mock<ItemsApiService> {
                    onBlocking {
                        getProductsByQuery(
                            anyString(),
                            anyInt()
                        )
                    } doThrow RuntimeException("HttpException")
                }

                val productPageKeyedDataSource =
                    ProductPageKeyedDataSource(itemsApiService, "iphone")

                // WHEN
                productPageKeyedDataSource.loadAfter(
                    PageKeyedDataSource.LoadParams(
                        2,
                        24
                    ), mock()
                )

                // THEN
                assertTrue(productPageKeyedDataSource.transactionState.getOrAwaitValue() is TransactionState.Fail)
            }
        }
    }
}