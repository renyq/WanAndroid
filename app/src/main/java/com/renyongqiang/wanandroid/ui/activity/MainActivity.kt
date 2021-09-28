package com.renyongqiang.wanandroid.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.renyongqiang.wanandroid.R
import com.renyongqiang.wanandroid.ui.home.HomeViewModel

private const val TAG = "MainActivity"

class MainActivity : BaseActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val appBar: AppBarLayout = findViewById(R.id.app_bar_layout)
        title = findViewById(R.id.tv_title)
        val headBack: ImageView = findViewById(R.id.iv_head_back)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            run {
                when (destination.id) {
                    R.id.navigation_content -> {
                        navView.visibility = View.GONE
                        appBar.visibility = View.VISIBLE
                    }
                    else -> {
                        navView.visibility = View.VISIBLE
                        appBar.visibility = View.GONE
                    }
                }
            }
        }
        headBack.setOnClickListener { navController.popBackStack() }
        Log.i(TAG, "onCreate: viewModel=${homeViewModel},title=${homeViewModel.title}")
    }

    override fun onResume() {
        super.onResume()
        subjectUi(title)
    }

    private fun subjectUi(title: TextView) {
//        homeViewModel.title.observe(this) { t ->
//            Log.i(TAG, "subjectUi: title=$t")
//            title.text = t
//        }

        homeViewModel.title.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                Log.i(TAG, "onChanged: $t")
                title.text = t
            }
        })
    }
}