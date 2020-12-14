package dev.sariego.androidnews.framework.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles WHERE deleted = 0 ORDER BY created DESC")
    fun observeNonDeletedArticles(): Flow<List<LocalDataArticle>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOnlyNewArticles(vararg articles: LocalDataArticle)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(article: LocalDataArticle)
}