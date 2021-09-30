package com.renyongqiang.wanandroid.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.renyongqiang.wanandroid.adapter.FooterAdapter
import com.renyongqiang.wanandroid.adapter.HomeAdapter
import com.renyongqiang.wanandroid.databinding.FragmentHomeBinding
import com.renyongqiang.wanandroid.model.bean.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val adapter = HomeAdapter()
    private var loadJob: Job? = null
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView: ")
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
        val recyclerView = binding.recyclerView
        adapter.setItemClickListener(object : HomeAdapter.OnItemClick {
            override fun gotoArticle(item: Article) {
                Log.i(TAG, "gotoArticle: viewModel=${homeViewModel},title=${homeViewModel.title}")
                val hasObservers = homeViewModel.title.hasObservers()
                val hasActiveObservers = homeViewModel.title.hasActiveObservers()
                Log.i(
                    TAG,
                    "gotoArticle: hasObservers=$hasObservers,hasActiveObservers=$hasActiveObservers"
                )
                homeViewModel.title.setValue(item.title)
            }
        })
        recyclerView.adapter = adapter.withLoadStateFooter(
            FooterAdapter(adapter::retry)
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        load()
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is LoadState.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(
                        activity,
                        "Load Error: ${state.error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun load() {
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            Log.i(TAG, "load: ")
            homeViewModel.requestArticles(0).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}