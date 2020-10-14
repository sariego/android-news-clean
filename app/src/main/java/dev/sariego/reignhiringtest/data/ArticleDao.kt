package dev.sariego.reignhiringtest.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article WHERE deleted = 0 ORDER BY created DESC")
    fun getArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticles(vararg articles: Article)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(article: Article)
}