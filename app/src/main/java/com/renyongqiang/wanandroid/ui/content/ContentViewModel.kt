package com.renyongqiang.wanandroid.ui.content

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renyongqiang.wanandroid.App.Companion.context
import androidx.core.content.ContextCompat.startActivity

import android.content.pm.ResolveInfo

import android.os.Build

import android.webkit.WebView
import androidx.core.content.ContextCompat
import java.lang.Exception
import java.net.URISyntaxException


class ContentViewModel : ViewModel() {
    val webViewClient by lazy { Client() }
    var progressVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)

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
        }

        //        override fun shouldOverrideUrlLoading(
//            view: WebView?,
//            request: WebResourceRequest?
//        ): Boolean {
//            if (request?.url == null) return false
//            try {
//                if (!request.url.toString()
//                        .startsWith("http://") && !request.url.toString().startsWith("https://")
//                ) {
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(request.url.toString()))
//                    startActivity(context,intent,null)
//                    return true
//                }
//            } catch (e: Exception) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
//                return true //没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
//            }
//            return super.shouldOverrideUrlLoading(view, request)
//        }
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
                    Log.d("yxx", "处理自定义scheme-->$newurl")
                    try {
                        // 以下固定写法
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(newurl)
                        )
                        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                                or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(context, intent, null)
                    } catch (e: Exception) {
                        // 防止没有安装的情况
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

    companion object {
        private const val TAG = "ContentViewModel"
    }
}

fun String.startsWith(prefix: String): Boolean {
    var to = 0
    var po = 0
    var pc: Int = prefix.length
    // Note: toffset might be near -1>>>1.
    // Note: toffset might be near -1>>>1.
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
