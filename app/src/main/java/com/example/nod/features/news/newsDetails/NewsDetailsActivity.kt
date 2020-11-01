package com.example.nod.features.news.newsDetails

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.IdRes
import com.example.nod.R
import com.example.nod.common.BUND_CONTENT_URL
import com.example.nod.common.BaseActivity
import com.example.nod.common.inProgress
import com.example.nod.common.progressComplete
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.partial_news_content_toolbar.*

/**
 * Activity that displays the news information by fetching the data from the provided
 * content URL and loading the data into the respective [WebView].
 *
 * The web page is cached locally once the content has been loaded, and the user can view the
 * cached information when offline (this functionality also works when the user closes and reopens
 * the app, provided the data has been cached once online).
 */
// Setting the JavaScript to be enabled for the WebView so that the
// page does not load in an external browser.
@SuppressLint("SetJavaScriptEnabled")
class NewsDetailsActivity : BaseActivity() {

    private var contentUrl: String? = null

    /*
    WebView customised for the following properties:
    1. It is JavaScript enabled so that all the websites can load inside it without redirecting to external browsers.
    2. It maintains a local cache; Once cached the website can be opened offline (content is loaded once online).
     */
    private val browser: WebView by lazy {

        wvNewsContent.apply {
            settings.setAppCachePath(application.cacheDir.absolutePath)
            settings.allowFileAccess = true
            settings.setAppCacheEnabled(true)
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webViewClient = WebViewController()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        intent?.run {
            if (hasExtra(BUND_CONTENT_URL)) contentUrl = extras?.getString(BUND_CONTENT_URL)
        }

        // Setting up the toolbar.
        setupToolbar()
        // Loading the news content in the WebView.
        setupSwipeToRefresh()
        loadPageContent()
    }

    override fun getRootView(): ViewGroup? {
        return clRoot
    }

    override fun getNetworkAvailability(isConnected: Boolean) {
        // Nothing to do.
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        /**
         * Performs a particular action based on the item clicked.
         */
        fun performAction(@IdRes itemId: Int): Boolean {
            return when (itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> false
            }
        }

        return item?.let { performAction(it.itemId) } ?: super.onOptionsItemSelected(item)
    }

    /**
     * Function to setup the news container's Toolbar.
     */
    private fun setupToolbar() {
        setSupportActionBar(tbNewsContentToolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }

    /**
     * Function to load the [contentUrl] in the [browser] if network connection is available.
     */
    private fun loadPageContent() {
        tvContentTitle?.text = if (!contentUrl.isNullOrBlank()) {
            browser.loadUrl(contentUrl)
            contentUrl
        } else {
            getString(R.string.content_loading_failure)
        }
    }

    /**
     * Setting up swipe-to-refresh to reload the data in the [WebView].
     */
    private fun setupSwipeToRefresh() {
        srlRefreshWebPage?.setOnRefreshListener {
            loadPageContent()
        }
    }

    /**
     * Custom [WebViewClient] to load the page content in [wvNewsContent] instead of
     * external browsers.
     */
    inner class WebViewController : WebViewClient() {

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            srlRefreshWebPage?.progressComplete()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e(TAG, "${error?.description}")
            }
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            srlRefreshWebPage?.inProgress()
            tvContentTitle?.text = view?.url
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            srlRefreshWebPage?.progressComplete()
        }
    }

    companion object {
        val TAG: String = NewsDetailsActivity::class.java.simpleName
    }
}
