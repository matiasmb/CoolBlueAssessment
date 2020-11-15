package com.matiasmb.coolblue.search.data.networking

import com.matiasmb.coolblue.TestData
import com.matiasmb.coolblue.search.data.model.Response
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class ItemsApiServiceImplTest {

    private lateinit var itemsApiService: ItemsApiServiceImpl

    @Test
    fun `searchProducts WHEN ItemsApiClient give a successful response SHOULD return a non empty list of product inside the response`() {
        runBlocking {
            // GIVEN
            val apiClient = mock<ItemsApiClient> {
                onBlocking {
                    searchProducts(
                        anyString(),
                        anyInt()
                    )
                } doReturn TestData.dataProductsResponse
            }
            itemsApiService = ItemsApiServiceImpl(apiClient)

            //WHEN
            val response: Flow<Response> = itemsApiService.getProductsByQuery("iphone", 1)

            //THEN
            response.collect {
                assertTrue(it.products.isNotEmpty())
            }
        }
    }

    @Test
    fun `searchProducts WHEN ItemsApiClient give a successful response SHOULD return an empty list of product inside the response`() {
        runBlocking {
            // GIVEN
            val apiClient = mock<ItemsApiClient> {
                onBlocking {
                    searchProducts(
                        anyString(),
                        anyInt()
                    )
                } doReturn TestData.dataProductsResponseEmpty
            }
            itemsApiService = ItemsApiServiceImpl(apiClient)

            //WHEN
            val response: Flow<Response> = itemsApiService.getProductsByQuery("iphone", 1)

            //THEN
            response.collect {
                assertTrue(it.products.isEmpty())
            }
        }
    }
}