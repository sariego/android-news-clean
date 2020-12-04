package dev.sariego.reignhiringtest.framework.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles WHERE deleted = 0 ORDER BY created DESC")
    fun getNonDeletedArticles(): LiveData<List<LocalDataArticle>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOnlyNewArticles(vararg articles: LocalDataArticle)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(article: LocalDataArticle)
}