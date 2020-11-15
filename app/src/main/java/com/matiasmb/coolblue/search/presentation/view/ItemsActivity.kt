package com.matiasmb.coolblue.search.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.paging.PagedList
import com.google.android.material.snackbar.Snackbar
import com.matiasmb.coolblue.R
import com.matiasmb.coolblue.search.presentation.adapter.ItemsAdapter
import com.matiasmb.coolblue.search.presentation.model.ItemView
import com.matiasmb.coolblue.search.presentation.model.TransactionState
import com.matiasmb.coolblue.search.presentation.viewmodel.ItemsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ItemsActivity : AppCompatActivity() {

    private val viewModel: ItemsViewModel by viewModel()
    private val itemsAdapter by lazy { ItemsAdapter(context = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
        setObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val myActionMenuItem = menu?.findItem(R.id.action_search)
        val searchView = myActionMenuItem?.actionView as? SearchView
        searchView?.setIconifiedByDefault(false)
        searchView?.isIconified = false
        searchView?.queryHint = resources.getString(R.string.find_your_product)
        myActionMenuItem?.expandActionView()
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.fetchItems(query)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //Not implemented
                return true
            }
        })
        return true
    }

    private fun setObservers() {
        viewModel.liveDataMerger.observe(this, ::loadScreen)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun loadList(productItems: PagedList<ItemView>?) {
        list_item.apply {
            adapter = itemsAdapter
            setHasFixedSize(true)
        }
        itemsAdapter.submitList(productItems)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.search_products_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun loadScreen(itemsStateScreen: TransactionState<PagedList<ItemView>>) {
        when (itemsStateScreen) {
            TransactionState.Running -> {
                setLoadingVisibility(true)
            }
            is TransactionState.LoadData -> {
                loadList(itemsStateScreen.data)
            }
            TransactionState.EndLoadData -> {
                setLoadingVisibility(false)
            }
            TransactionState.Fail -> {
                setLoadingVisibility(false)
                showErrorScreen()
            }
        }
    }

    private fun setLoadingVisibility(shouldShowLoading: Boolean) {
        if (shouldShowLoading) {
            loading_background.visibility = VISIBLE
            loading.visibility = VISIBLE
        } else {
            loading_background.visibility = GONE
            loading.visibility = GONE
        }
    }

    private fun showErrorScreen() {
        Snackbar.make(
            coordinator_layout,
            getString(R.string.error_searching_for_products),
            Snackbar.LENGTH_LONG
        ).apply {
            view.setBackgroundColor(ContextCompat.getColor(this@ItemsActivity, R.color.red))
        }.show()
    }
}
