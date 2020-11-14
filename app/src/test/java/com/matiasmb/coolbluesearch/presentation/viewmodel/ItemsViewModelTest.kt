package com.matiasmb.coolbluesearch.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.matiasmb.coolbluesearch.CoroutinesRule
import com.matiasmb.coolbluesearch.TestData
import com.matiasmb.coolbluesearch.data.networking.ItemsApiService
import com.matiasmb.coolbluesearch.domain.interactor.DomainInteractorImpl
import com.matiasmb.coolbluesearch.getOrAwaitValue
import com.matiasmb.coolbluesearch.presentation.model.TransactionState
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
    fun `fetchItems SHOULD call products liveData WITH a list of products AND stateScreen liveData should be success`() {
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
                assertTrue(viewModel.stateScreen.getOrAwaitValue() is TransactionState.Success)
            }
        }
    }
}