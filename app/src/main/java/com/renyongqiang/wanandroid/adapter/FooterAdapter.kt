package com.renyongqiang.wanandroid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.renyongqiang.wanandroid.R

class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val retryButton: Button = itemView.findViewById(R.id.retry_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_loadmore, parent, false)
        val holder = ViewHolder(view)
        holder.retryButton.setOnClickListener {
            retry()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.progressBar.isVisible = loadState is LoadState.Loading
        holder.retryButton.isVisible = loadState is LoadState.Error
        holder.progressBar.visibility = if (loadState is LoadState.Loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
        holder.retryButton.visibility = if (loadState is LoadState.Error) {
            View.VISIBLE
        } else {
            View.GONE
        }
        Log.d(TAG, "onBindViewHolder: $loadState")
        Log.d(TAG, "onBindViewHolder:retryButton ${holder.retryButton.isVisible}")
        Log.d(TAG, "onBindViewHolder:progressBar ${holder.progressBar.isVisible}")
    }

    companion object {
        private const val TAG = "FooterAdapter"
    }
}