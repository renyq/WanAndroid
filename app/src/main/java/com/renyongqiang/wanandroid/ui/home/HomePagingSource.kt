/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.renyongqiang.wanandroid.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.renyongqiang.wanandroid.api.ApiService
import com.renyongqiang.wanandroid.model.bean.Article

private const val HOME_STARTING_PAGE_INDEX = 0

class HomePagingSource(
    private val service: ApiService,
    private val num: Int
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: HOME_STARTING_PAGE_INDEX
        return try {
            val response = service.getArticles(page)
            val photos = response.data.datas
            LoadResult.Page(
                data = photos,
                prevKey = if (page == HOME_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data.pageCount) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }
}
