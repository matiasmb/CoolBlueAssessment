package com.matiasmb.coolblue.search.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.matiasmb.coolblue.CoroutinesRule
import com.matiasmb.coolblue.TestData
import com.matiasmb.coolblue.search.data.networking.ItemsApiService
import com.matiasmb.coolblue.search.domain.interactor.DomainInteractorImpl
import com.matiasmb.coolblue.getOrAwaitValue
import com.matiasmb.coolblue.search.presentation.model.TransactionState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@ExperimentalCoroutinesApi
class ItemsViewModelTest {

    private lateinit var viewModel: ItemsViewModel

    @get:Rule
    var coroutinesRule = CoroutinesRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `fetchItems SHOULD call products liveData WITH a list of products AND stateScreen liveData should be en load data`() {
        runBlocking {
            launch(Dispatchers.Main) {

                //GIVEN
                val itemsApiService = mock<ItemsApiService> {
                    onBlocking {
                        getProductsByQuery(
                            ArgumentMatchers.anyString(),
                            ArgumentMatchers.anyInt()
                        )
                    } doReturn TestData.serviceSuccessResponse
                }
                val domainInteractor = DomainInteractorImpl(itemsApiService)
                viewModel = ItemsViewModel(domainInteractor)

                //WHEN
                viewModel.fetchItems("iphone")

                //THEN
                assertTrue(viewModel.products.getOrAwaitValue().snapshot().isNotEmpty())
                assertTrue(viewModel.stateScreen.getOrAwaitValue() is TransactionState.EndLoadData)
            }
        }
    }

    @Test
    fun `fetchItems SHOULD call products liveDataMerger WITH load data state AND after load data is complete call the mediator live data with EndLoadData`() {
        runBlocking {
            launch(Dispatchers.Main) {

                //GIVEN
                val itemsApiService = mock<ItemsApiService> {
                    onBlocking {
                        getProductsByQuery(
                            ArgumentMatchers.anyString(),
                            ArgumentMatchers.anyInt()
                        )
                    } doReturn TestData.serviceSuccessResponse
                }
                val domainInteractor = DomainInteractorImpl(itemsApiService)
                viewModel = ItemsViewModel(domainInteractor)

                //WHEN
                viewModel.fetchItems("iphone")

                //THEN
                assertTrue(viewModel.liveDataMerger.getOrAwaitValue() is TransactionState.LoadData)
                assertTrue(viewModel.liveDataMerger.getOrAwaitValue() is TransactionState.EndLoadData)
            }
        }
    }
}