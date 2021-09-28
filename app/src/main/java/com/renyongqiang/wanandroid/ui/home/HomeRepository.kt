package com.renyongqiang.wanandroid.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.renyongqiang.wanandroid.api.ApiService
import com.renyongqiang.wanandroid.http.RetrofitHelper
import com.renyongqiang.wanandroid.model.bean.Article
import kotlinx.coroutines.flow.Flow

class HomeRepository {
    private val service: ApiService by lazy { RetrofitHelper.service }

    fun requestArticles(num: Int): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { HomePagingSource(service, num) }
        ).flow
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}