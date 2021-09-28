package com.renyongqiang.wanandroid.data

import androidx.room.*
import com.renyongqiang.wanandroid.model.bean.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article ORDER BY id")
    fun getArticles(): Flow<List<Article>>

    @Query("SELECT * FROM article WHERE id = :id")
    fun getArticle(id: String): Flow<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Article>)

    @Query("DELETE FROM article")
    suspend fun clearAll()

    @Delete
    suspend fun deleteArticle(article: Article)
}
