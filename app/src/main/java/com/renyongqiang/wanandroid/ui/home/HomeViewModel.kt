package com.renyongqiang.wanandroid.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.renyongqiang.wanandroid.model.bean.Article
import kotlinx.coroutines.flow.Flow

private const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository = HomeRepository()
    val title: MutableLiveData<String> = MutableLiveData()

    fun requestArticles(num: Int): Flow<PagingData<Article>> {
        return repository.requestArticles(num).cachedIn(viewModelScope)
    }

    fun setTitle(t: String) {
        Log.i(TAG, "setTitle: $t")
        title.value = t
    }


}