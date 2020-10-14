package dev.sariego.reignhiringtest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Article(
    @PrimaryKey var id: Int,
    var title: String,
    var author: String,
    var created: Date,
    var url: String? = null,
    var deleted: Boolean = false,
)