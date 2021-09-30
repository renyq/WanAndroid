package com.renyongqiang.wanandroid.ui.content

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renyongqiang.wanandroid.App.Companion.context
import java.net.URISyntaxException


class ContentViewModel : ViewModel() {
    private val _loadProgress = MutableLiveData(0)

    val webViewClient by lazy { Client() }
    val chromeClient by lazy { ChromeClient() }

    var progressVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val loadProgress: LiveData<Int> = _loadProgress
    var webTitle: MutableLiveData<String> = MutableLiveData()

    inner class Client : WebViewClient() {
        override fun onReceivedError(
            view: WebView, request: WebResourceRequest,
            error: WebResourceError
        ) {
            Log.d(TAG, "onReceivedError: ")
            super.onReceivedError(view, request, error)
            progressVisibility.value = View.GONE
        }

        override fun onPageFinished(view: WebView, url: String) {
            Log.d(Companion.TAG, "onPageFinished: ")
            super.onPageFinished(view, url)
            progressVisibility.value = View.GONE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.d(TAG, "onPageStarted: $url")
            progressVisibility.value = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView?, newurl: String): Boolean {
            try {
                //处理intent协议
                if (newurl.startsWith("intent://")) {
                    val intent: Intent
                    try {
                        intent = Intent.parseUri(newurl, Intent.URI_INTENT_SCHEME)
                        intent.addCategory("android.intent.category.BROWSABLE")
                        intent.component = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                            intent.selector = null
                        }
                        val resolves = context.packageManager.queryIntentActivities(intent, 0)
                        if (resolves.size > 0) {
                            startActivity(context, intent, null)
                        }
                        return true
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }
                }
                // 处理自定义scheme协议
                if (!newurl.startsWith("http") || !newurl.startsWith("https")) {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newurl))
                        intent.flags =
                            (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(context, intent, null)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return super.shouldOverrideUrlLoading(view, newurl)
        }
    }

    inner class ChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            Log.i(TAG, "onProgressChanged: $newProgress")
            _loadProgress.value = newProgress
            if (newProgress == 100) {
                progressVisibility.value = View.GONE
            }
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            webTitle.value = title
        }
    }

    companion object {
        private const val TAG = "ContentViewModel"
    }
}

fun String.startsWith(prefix: String): Boolean {
    var to = 0
    var po = 0
    var pc: Int = prefix.length
    if (0 > length - pc) {
        return false
    }
    while (--pc >= 0) {
        if (get(to++) != prefix[po++]) {
            return false
        }
    }
    return true
}
