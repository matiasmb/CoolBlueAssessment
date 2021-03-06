package com.matiasmb.coolblue.search.data.networking

import com.matiasmb.coolblue.search.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemsApiClient {

    /**
     * List products for the specified query.
     *
     * @param query - product to search.
     * @param page - current page.
     * @return a list of products.
     */
    @GET("search")
    suspend fun searchProducts(@Query("query") query: String, @Query("page") page: Int): Response
}