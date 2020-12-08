package dev.sariego.reignhiringtest.framework.net

import android.net.Uri
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import dev.sariego.reignhiringtest.data.remote.ArticlesRemoteDataSource
import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.framework.di.qualifier.ServerUrl
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class HackerNewsRemoteDataSource @Inject constructor(
    @ServerUrl private val serverUrl: String,
    private val queue: RequestQueue,
    private val deserializer: HackerNewsArticleDeserializer,
) : ArticlesRemoteDataSource {

    override fun get(): List<Article> {
        val url = Uri.parse(serverUrl)
            .buildUpon()
            .appendPath(HackerNewsApi.SEARCH_PATH)
            .appendQueryParameter(
                HackerNewsApi.SEARCH_QUERY_PARAM_KEY,
                HackerNewsApi.SEARCH_QUERY_PARAM_VALUE,
            )
            .build()
            .toString()
        val future = RequestFuture.newFuture<JSONObject>()

        queue.add(JsonObjectRequest(url, null, future, future))

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