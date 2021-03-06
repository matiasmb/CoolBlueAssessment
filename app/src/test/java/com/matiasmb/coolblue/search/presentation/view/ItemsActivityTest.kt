package com.matiasmb.coolblue.search.presentation.view

import android.os.Build
import android.view.View.GONE
import android.view.View.VISIBLE
import com.matiasmb.coolblue.R
import com.matiasmb.coolblue.TestData.itemViewList
import com.matiasmb.coolblue.mockPagedList
import com.matiasmb.coolblue.search.presentation.model.TransactionState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.fakes.RoboMenu

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
        shadowActivity.loadScreen(TransactionState.EndLoadData)
        assertTrue(shadowActivity.loading.visibility == GONE)
        assertTrue(shadowActivity.loading_background.visibility == GONE)
    }

    @Test
    fun `loadScreen SHOULD hide loading items because a failure was received`() {
        shadowActivity.loadScreen(TransactionState.Fail)
        assertTrue(shadowActivity.loading.visibility == GONE)
        assertTrue(shadowActivity.loading_background.visibility == GONE)
    }

    @Test
    fun `loadScreen SHOULD hide loading items because a load data was received`() {
        shadowActivity.loadScreen(TransactionState.LoadData(mockPagedList(itemViewList)))
        assertTrue(shadowActivity.loading.visibility == GONE)
        assertTrue(shadowActivity.loading_background.visibility == GONE)
    }

    @Test
    fun `loadList SHOULD load into the recyclerview one item`() {
        shadowActivity.loadList(mockPagedList(itemViewList))
        assertTrue(shadowActivity.list_item.adapter?.itemCount == 1)
    }

    @Test
    fun `onCreateOptionsMenu SHOULD load into the recyclerview one item`() {
        val menu = RoboMenu()
        shadowActivity.onCreateOptionsMenu(menu)
        assertTrue(menu.getItem(0).title == shadowActivity.resources.getString(R.string.search))
    }
}