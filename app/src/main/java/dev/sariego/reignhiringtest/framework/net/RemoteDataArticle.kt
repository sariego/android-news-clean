package dev.sariego.reignhiringtest.framework.net

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class RemoteDataArticle(
    @Json(name = "story_id") val id: Int?,
    val title: String?,
    @Json(name = "story_title") val storyTitle: String?,
    val author: String?,
    val url: String?,
    @Json(name = "story_url") val storyUrl: String?,
    @Json(name = "created_at") val created: Date?,
)