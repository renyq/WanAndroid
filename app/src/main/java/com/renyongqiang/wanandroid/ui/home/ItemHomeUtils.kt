package com.renyongqiang.wanandroid.ui.home

import android.view.View
import com.renyongqiang.wanandroid.model.bean.Article

object ItemHomeUtils {

    fun getTopVisibility(article: Article): Int {
        return if (article.top == "1") View.VISIBLE else View.GONE
    }

    fun getFreshVisibility(article: Article): Int {
        return if (article.fresh) View.VISIBLE else View.GONE
    }

    fun getTagsVisibility(article: Article): Int {
        return if (article.tags.size > 0) View.VISIBLE else View.GONE
    }

    fun getTagName(article: Article): String {
        return if (article.tags.size > 0) article.tags[0].name else ""
    }

    fun getAuthor(article: Article): String {
        return if (article.author.isNotEmpty()) article.author else article.shareUser
    }

    fun getThumbnailVisibility(article: Article): Int {
        return if (article.envelopePic.isNotEmpty()) View.VISIBLE else View.GONE
    }

    fun getChapterName(article: Article): String {
        return when {
            article.superChapterName.isNotEmpty() and article.chapterName.isNotEmpty() ->
                "${article.superChapterName} / ${article.chapterName}"
            article.superChapterName.isNotEmpty() -> article.superChapterName
            article.chapterName.isNotEmpty() -> article.chapterName
            else -> ""
        }
    }
}