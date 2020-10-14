package dev.sariego.reignhiringtest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.sariego.reignhiringtest.data.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cell_article.*

class MainActivity : AppCompatActivity() {

    private val model: ArticlesViewModel by viewModels()
    private val adapter = ArticlesAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recyclerview setup
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycler.adapter = adapter

        // swipe to delete stuff
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                model.delete(adapter.items[viewHolder.adapterPosition])
            }
        }).attachToRecyclerView(recycler)

        // swipe to refresh stuff
        layoutSwipeRefresh.setOnRefreshListener {
            model.fetch()
                .invokeOnCompletion {
                    layoutSwipeRefresh.isRefreshing = false
                }
        }

        // model binding
        model.visibleArticles().observe(this) { articles ->
            adapter.apply {
                items = articles
                notifyDataSetChanged()
            }
        }
    }

    class ArticlesAdapter(var items: List<Article>) :
        RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

            init {
                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) { // check if item was deleted
                        val item = items[position]
                        item.url?.let {
                            CustomTabsIntent.Builder().build()
                                .launchUrl(itemView.context, Uri.parse(it))
                        }
                    }
                }
            }

            fun bind(article: Article) {
                textTitle.text = article.title
                textSubtitle.text = "${article.author} - ${article.created}"
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_article, parent, false)
        )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(items[position])

        override fun getItemCount(): Int = items.size
    }
}