package com.renyongqiang.wanandroid.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("setWebViewClient")
fun setWebViewClient(view: WebView, client: WebViewClient) {
    view.webViewClient = client
}

@BindingAdapter("display")
fun setDisplay(view: View, visibility: Int) {
    Log.d("renyq", "setDisplay: $visibility")
    view.visibility = visibility
}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("loadUrl")
fun loadUrl(view: WebView, url: String) {
    view.settings.apply {
        javaScriptEnabled = true
        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        domStorageEnabled = true
        setSupportZoom(true)
        defaultTextEncodingName = "utf-8"
        loadsImagesAutomatically = true
        javaScriptCanOpenWindowsAutomatically = true
        setRenderPriority(WebSettings.RenderPriority.HIGH)
        layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        loadWithOverviewMode = true

    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE;
    }
    view.loadUrl(url)
}