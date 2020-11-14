package com.matiasmb.coolbluesearch.domain.interactor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.matiasmb.coolbluesearch.CoroutinesRule
import com.matiasmb.coolbluesearch.TestData
import com.matiasmb.coolbluesearch.data.networking.ItemsApiService
import com.matiasmb.coolbluesearch.getOrAwaitValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@ExperimentalCoroutinesApi
class DomainInteractorImplTest {

    @get:Rule
    var coroutinesRule = CoroutinesRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var domainInteractor: DomainInteractorImpl

    @Test
    fun `getPagedListConfig SHOULD config the paged list with page size of 24 and get the place holder disable`() {
        // GIVEN
        domainInteractor = DomainInteractorImpl(mock())

        //WHEN
        val config = domainInteractor.getPagedListConfig()

        //THEN
        assertTrue(24 == config.pageSize)
        assertFalse(config.enablePlaceholders)
    }

    @Test
    fun `getProductDataSourceFactory SHOULD create product data source factory and provide a mutable live data`() {
        // GIVEN
        domainInteractor = DomainInteractorImpl(mock())

        //WHEN
        val productDataSourceFactory = domainInteractor.getProductDataSourceFactory("iphone")

        //THEN
        assertNotNull(productDataSourceFactory.source)
    }

    @Test
    fun `performSearch SHOULD get a paged list not empty`() {
        // GIVEN
        val itemsApiService = mock<ItemsApiService> {
            onBlocking {
                getProductsByQuery(
                    ArgumentMatchers.anyString(),
                    ArgumentMatchers.anyInt()
                )
            } doReturn TestData.serviceSuccessResponse
        }
        domainInteractor = DomainInteractorImpl(itemsApiService)

        //WHEN
        val listing = domainInteractor.performSearch("iphone")

        //THEN
        assertTrue(listing.pagedList.getOrAwaitValue().snapshot().isNotEmpty())
    }
}