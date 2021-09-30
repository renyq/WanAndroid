package com.renyongqiang.wanandroid.adapter

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
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

@BindingAdapter("setWebChromeClient")
fun setWebChromeClient(view: WebView, client: WebChromeClient) {
    view.webChromeClient = client
}

@BindingAdapter("progressScaled")
fun setProgress(progressBar: ProgressBar, progress: Int) {
    progressBar.progress = progress
}

@BindingAdapter("progressVisibility")
fun setProgressVisibility(progressBar: ProgressBar, visible: Int) {
    progressBar.visibility = visible
}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("loadUrl")
fun loadUrl(view: WebView, url: String) {
    view.settings.apply {
        javaScriptEnabled = true
        mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        domStorageEnabled = true
        setSupportZoom(true)
        defaultTextEncodingName = "utf-8"
        loadsImagesAutomatically = true
        javaScriptCanOpenWindowsAutomatically = true
        layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        loadWithOverviewMode = true
    }
    view.loadUrl(url)
}