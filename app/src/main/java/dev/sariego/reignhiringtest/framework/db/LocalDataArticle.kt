package dev.sariego.reignhiringtest.framework.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "articles")
data class LocalDataArticle(
    @PrimaryKey var id: Int,
    var title: String,
    var author: String,
    var created: Date,
    var url: String? = null,
    var deleted: Boolean = false,
)