package com.example.nod.features.news.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.nod.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.*

/**
 * Splash [Fragment] that is used to display the application name every time the
 * user opens that app.
 */
class SplashFragment : Fragment() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiScope.launch {
            delay(SPLASH_DELAY_MILLI)
            clRoot?.findNavController()
                ?.navigate(R.id.action_splashFragment_to_newsDetailsFragment2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancelling any pending jobs.
        job.cancel()
    }

    companion object {
        val TAG: String = SplashFragment::class.java.simpleName
        const val SPLASH_DELAY_MILLI = 1000L
    }
}
