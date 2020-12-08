package dev.sariego.reignhiringtest.framework.net

import dev.sariego.reignhiringtest.domain.entity.Article
import org.json.JSONException
import org.json.JSONObject
import java.time.Instant
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HackerNewsArticleDeserializer @Inject constructor() {

    fun JSONObject.deserialize(): List<Article> {
        val articles = ArrayList<Article>()
        val fetched = this.getJSONArray("hits")
        for (i in 0 until fetched.length()) {
            try {
                articles.add(fetched.getJSONObject(i).toArticle())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return articles
    }

    private fun JSONObject.toArticle() = this.let {
        Article(
            id = it.getInt("story_id"),
            title = it.getString("story_title") ?: it.getString("title"),
            author = it.getString("author"),
            url = it.getString("story_url") ?: it.getString("url"),
            created = Date(Instant.parse(it.getString("created_at")).toEpochMilli())
        )
    }
}