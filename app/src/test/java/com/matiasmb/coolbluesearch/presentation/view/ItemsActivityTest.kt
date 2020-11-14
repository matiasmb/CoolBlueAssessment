package com.matiasmb.coolbluesearch.presentation.view

import android.content.Intent
import android.os.Build
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.paging.PagedList
import com.matiasmb.coolbluesearch.TestData.itemViewList
import com.matiasmb.coolbluesearch.mockPagedList
import com.matiasmb.coolbluesearch.presentation.model.ItemView
import com.matiasmb.coolbluesearch.presentation.model.TransactionState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ItemsActivityTest : KoinTest {

    private lateinit var shadowActivity: ItemsActivity

    @Before
    fun setUp() {
        shadowActivity =
            Robolectric.buildActivity(ItemsActivity::class.java).create().resume().get()
    }

    @Test
    fun `supportActionBar SHOULD have a title set`() {
        assertEquals("Search Products", shadowActivity.supportActionBar?.title)
    }

    @Test
    fun `loadScreen SHOULD show loading items`() {
        shadowActivity.loadScreen(TransactionState.Running)
        assertTrue(shadowActivity.loading.visibility == VISIBLE)
        assertTrue(shadowActivity.loading_background.visibility == VISIBLE)
    }

    @Test
    fun `loadScreen SHOULD hide loading items because a success was received`() {
        shadowActivity.loadScreen(TransactionState.Success)
        assertTrue(shadowActivity.loading.visibility == GONE)
        assertTrue(shadowActivity.loading_background.visibility == GONE)
    }

    @Test
    fun `loadScreen SHOULD hide loading items because a failure was received`() {
        shadowActivity.loadScreen(TransactionState.Success)
        assertTrue(shadowActivity.loading.visibility == GONE)
        assertTrue(shadowActivity.loading_background.visibility == GONE)
    }

    @Test
    fun `loadlist SHOULD hide loading items because a failure was received`() {
        shadowActivity.loadList(mockPagedList(itemViewList))
        assertTrue(shadowActivity.list_item.adapter?.itemCount == 1)
    }
}