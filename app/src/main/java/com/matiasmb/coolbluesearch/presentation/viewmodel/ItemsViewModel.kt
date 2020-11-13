package com.matiasmb.coolbluesearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matiasmb.coolbluesearch.data.model.Resource
import com.matiasmb.coolbluesearch.domain.DomainInteractor
import com.matiasmb.coolbluesearch.presentation.model.ItemsStateScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemsViewModel(private val domainInteractor: DomainInteractor) : ViewModel() {

    val stateScreen: LiveData<ItemsStateScreen> get() = _stateScreen
    private val _stateScreen: MutableLiveData<ItemsStateScreen> = MutableLiveData()

    fun fetchItems(query: String) {
        viewModelScope.launch {
            domainInteractor.performSearch(query).collect { response ->
                when (response) {
                    is Resource.Success -> _stateScreen.value =
                        ItemsStateScreen.ShowItems(response.data)
                    Resource.Failure -> _stateScreen.value = ItemsStateScreen.ShowErrorLoading
                }
            }
        }
    }
}