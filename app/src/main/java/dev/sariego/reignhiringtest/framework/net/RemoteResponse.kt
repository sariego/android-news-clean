package dev.sariego.reignhiringtest.framework.net

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteResponse(val hits: List<RemoteDataArticle> = emptyList())