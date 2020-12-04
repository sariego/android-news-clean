package dev.sariego.reignhiringtest.framework.net

import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import dev.sariego.reignhiringtest.data.remote.ArticlesRemoteDataSource
import dev.sariego.reignhiringtest.domain.entity.Article
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class HackerNewsRemoteDataSource @Inject constructor(
    private val queue: RequestQueue
) : ArticlesRemoteDataSource {

    private val deserializer = HackerNewsArticleDeserializer()

    override fun get(): List<Article> {
        val future = RequestFuture.newFuture<JSONObject>()
        queue.add(
            JsonObjectRequest(
                "https://hn.algolia.com/api/v1/search_by_date?query=android",
                null,
                future,
                future
            )
        )
        try {
            val response = future.get()
            return with(deserializer) {
                response.deserialize()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } // todo handle exceptions

        return emptyList()
    }
}