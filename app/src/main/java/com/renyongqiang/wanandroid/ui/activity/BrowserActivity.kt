package com.renyongqiang.wanandroid.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.renyongqiang.wanandroid.Constant
import com.renyongqiang.wanandroid.R
import com.renyongqiang.wanandroid.databinding.ActivityBrowserBinding
import com.renyongqiang.wanandroid.ui.content.ContentViewModel
import com.renyongqiang.wanandroid.ui.content.startsWith

private const val TAG = "BrowserActivity"

class BrowserActivity : AppCompatActivity() {

    private val args: BrowserActivityArgs by navArgs()
    private val contentViewModel: ContentViewModel by viewModels()
    private lateinit var binding: ActivityBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_browser)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val ivBack = findViewById<ImageView>(R.id.iv_head_back)
        binding.apply {
            link = if (args.link.startsWith("/")) {
                Constant.BASE_URL + args.link
            } else {
                args.link
            }
            Log.i(TAG, "onCreate: $link")
            title = args.title
            viewModel = contentViewModel
            lifecycleOwner = this@BrowserActivity
            tvTitle.text = title
        }
        contentViewModel.webTitle.observe(this) { tvTitle.text = it }
        ivBack.setOnClickListener {
            back()
        }
    }

    override fun onBackPressed() {
        back()
    }

    private fun back() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}