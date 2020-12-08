package dev.sariego.reignhiringtest.presentation

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.sariego.reignhiringtest.databinding.ActivityArticlesBinding
import dev.sariego.reignhiringtest.presentation.adapter.ArticlesAdapter
import dev.sariego.reignhiringtest.presentation.adapter.onArticleClick
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesActivity : AppCompatActivity() {

    private val model: ArticlesViewModel by viewModels()
    private lateinit var binding: ActivityArticlesBinding

    @Inject
    lateinit var adapter: ArticlesAdapter

    @Inject
    lateinit var browserIntent: CustomTabsIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSwipeToRefresh()

        // model binding
        model.visibleArticles().observe(this) { articles ->
            adapter.apply {
                items = articles
                notifyDataSetChanged()
            }
        }

        // adapter binding
        adapter.onArticleClick { _, uri ->
            if (uri != null) {
                browserIntent.launchUrl(this, uri)
            } else {
                Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recycler.adapter = adapter

        setupSwipeToDelete()
    }

    private fun setupSwipeToDelete() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                model.delete(adapter.items[viewHolder.adapterPosition])
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                // to draw delete label
                RecyclerViewSwipeDecorator.Builder(
                    c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive
                ).addBackgroundColor(Color.parseColor("#CC0000"))
                    .setSwipeLeftLabelColor(Color.parseColor("#FFFFFF"))
                    .setSwipeRightLabelColor(Color.parseColor("#FFFFFF"))
                    .addSwipeLeftLabel("Delete")
                    .addSwipeRightLabel("Delete")
                    .create().decorate()

                super.onChildDraw(
                    c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive
                )
            }
        }).attachToRecyclerView(binding.recycler)
    }

    private fun setupSwipeToRefresh() {
        binding.layoutSwipeRefresh.setOnRefreshListener {
            model.update()
                .invokeOnCompletion {
                    binding.layoutSwipeRefresh.isRefreshing = false
                }
        }
    }

}