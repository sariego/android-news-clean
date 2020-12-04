package dev.sariego.reignhiringtest.presentation

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import dev.sariego.reignhiringtest.R
import dev.sariego.reignhiringtest.domain.entity.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cell_article.*
import org.ocpsoft.prettytime.PrettyTime
import javax.inject.Inject

class ArticlesAdapter @Inject constructor() :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var items: List<Article> = emptyList()
    private val pt = PrettyTime()

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
            textSubtitle.text = "${article.author} - ${pt.format(article.created)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cell_article, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}