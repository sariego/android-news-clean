package dev.sariego.reignhiringtest.framework.net

import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsService {

    @GET(HackerNewsApi.SEARCH_PATH)
    suspend fun search(
        @Query(HackerNewsApi.SEARCH_QUERY_PARAM_KEY) query: String
        = HackerNewsApi.SEARCH_QUERY_PARAM_VALUE
    ): RemoteResponse
}