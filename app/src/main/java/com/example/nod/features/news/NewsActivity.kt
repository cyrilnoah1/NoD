package com.example.nod.features.news

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.example.nod.R
import com.example.nod.common.BaseActivity
import kotlinx.android.synthetic.main.activity_news.*

/**
 * Activity that is used for handling the navigation of screens (Fragments) related to
 * the news information.
 *
 * The Activity is setup with Navigation Architecture Component to handle the navigation
 * between the screens.
 */
class NewsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
    }

    override fun getRootView(): ViewGroup? {
        return clRoot
    }

    override fun getNetworkAvailability(isConnected: Boolean) {
        if (isConnected) {
            Log.d(TAG, "Network connection is available.")
        } else {
            Log.e(TAG, "No internet connection available.")
        }
    }

    companion object {
        val TAG: String = NewsActivity::class.java.simpleName
    }
}
