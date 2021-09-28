package com.renyongqiang.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.renyongqiang.wanandroid.databinding.ItemHomeListBinding
import com.renyongqiang.wanandroid.model.bean.Article
import com.renyongqiang.wanandroid.ui.home.HomeFragmentDirections


/**
 * Created by chenxz on 2018/4/22.
 */
class HomeAdapter : PagingDataAdapter<Article, HomeAdapter.HomeViewHolder>(ArticleDiffCallback()) {

    private var mOnItemClick: OnItemClick? = null

    inner class HomeViewHolder(
        private val binding: ItemHomeListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.article?.let { article ->
                    mOnItemClick?.gotoArticle(article)
                    navigateToArticle(article.id, article.title, article.link, view)
                }
            }
        }

        private fun navigateToArticle(id: Int, title: String, link: String, view: View) {
            val direction = HomeFragmentDirections
                .actionNavigationHomeToNavigationContent(id, title, link)
            view.findNavController().navigate(direction)
        }


        fun bind(articleItem: Article) {
            binding.apply {
                article = articleItem
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setItemClickListener(click: OnItemClick) {
        mOnItemClick = click
    }

    interface OnItemClick {
        fun gotoArticle(item: Article)
    }
}

private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}

