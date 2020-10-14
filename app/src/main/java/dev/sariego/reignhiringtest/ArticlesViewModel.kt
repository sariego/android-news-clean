package dev.sariego.reignhiringtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import dev.sariego.reignhiringtest.data.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.time.Instant
import java.util.*
import java.util.concurrent.ExecutionException
import kotlin.collections.ArrayList

class ArticlesViewModel : ViewModel() {

    private val dao = App.db.articleDao()

    fun visibleArticles(): LiveData<List<Article>> {
        fetch()
        return dao.getArticles()
    }

    fun delete(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        article.deleted = true
        dao.updateArticle(article)
    }

    fun fetch() = viewModelScope.launch(Dispatchers.IO) {
        val future = RequestFuture.newFuture<JSONObject>()
        Volley
            .newRequestQueue(App.context)
            .add(
                JsonObjectRequest(
                    "https://hn.algolia.com/api/v1/search_by_date?query=android",
                    null,
                    future,
                    future
                )
            )
        try {
            val response = future.get()
            val fetched = response.getJSONArray("hits")
            val articles = ArrayList<Article>()

            for (i in 0 until fetched.length()) {
                try {
                    articles.add(
                        fetched.getJSONObject(i).let {
                            Article(
                                id = it.getInt("story_id"),
                                title = it.getString("story_title") ?: it.getString("title"),
                                author = it.getString("author"),
                                url = it.getString("story_url") ?: it.getString("url"),
                                created = Date(
                                    Instant.parse(it.getString("created_at")).toEpochMilli()
                                )
                            )
                        }
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            dao.insertArticles(*articles.toTypedArray())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } // todo handle exceptions
    }
}