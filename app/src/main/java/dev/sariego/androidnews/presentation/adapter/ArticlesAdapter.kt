package dev.sariego.androidnews.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import dev.sariego.androidnews.databinding.CellArticleBinding
import dev.sariego.androidnews.domain.entity.Article
import org.ocpsoft.prettytime.PrettyTime
import javax.inject.Inject

class ArticlesAdapter @Inject constructor() :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var items: List<Article> = emptyList()
    var onArticleClick: OnArticleClick? = null

    private val pt = PrettyTime()

    inner class ViewHolder(private val binding: CellArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) { // check if item was deleted
                    val item = items[position]
                    onArticleClick?.invoke(
                        position,
                        item.url?.takeIf(URLUtil::isValidUrl)?.let(Uri::parse)
                    )
                }
            }
        }

        fun bind(article: Article) {
            binding.textTitle.text = article.title
            binding.textSubtitle.text = "${article.author} - ${pt.format(article.created)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        CellArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}