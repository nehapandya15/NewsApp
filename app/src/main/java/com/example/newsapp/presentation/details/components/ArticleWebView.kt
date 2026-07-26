package com.example.newsapp.presentation.details.components

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleWebView(
    url: String,
    modifier: Modifier = Modifier
) {
    var loadingProgress by remember {
        mutableIntStateOf(0)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->

                WebView(context).apply {

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadsImagesAutomatically = true
                    }

                    webViewClient = WebViewClient()

                    webChromeClient = object : WebChromeClient() {

                        override fun onProgressChanged(
                            view: WebView?,
                            newProgress: Int
                        ) {
                            loadingProgress = newProgress
                        }
                    }

                    loadUrl(url)
                }
            }
        )

        if (loadingProgress < 100) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}