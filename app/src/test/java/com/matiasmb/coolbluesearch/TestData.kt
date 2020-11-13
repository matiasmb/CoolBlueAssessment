package com.matiasmb.coolbluesearch

import com.matiasmb.coolbluesearch.data.model.Product
import com.matiasmb.coolbluesearch.data.model.Resource
import com.matiasmb.coolbluesearch.presentation.model.ItemView
import kotlinx.coroutines.flow.flow

object TestData {
    val arrayDataList = arrayListOf(
        ItemView.ViewUserHeader("google"),
        ItemView.Product("kotlin", "https://github.com/test"),
    )
    val dataList = flow {
        emit(
            Resource.Success(
                arrayDataList
            )
        )
    }
    val dataRepoResponseEmpty = emptyList<Product>()
    val dataRepoResponseList = listOf(
        Product("kotlin", "https://github.com/test"),
    )

    val serviceSuccessResponse = flow { emit(Resource.Success(dataRepoResponseList)) }
    val serviceFailureResponse = flow { emit(Resource.Failure) }
}