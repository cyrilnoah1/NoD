package com.example.nod.features.news.sources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nod.R
import com.example.nod.common.*
import com.example.nod.features.news.NewsActivity
import com.example.nod.features.news.newsList.NewsListFragment
import kotlinx.android.synthetic.main.fragment_sources.*
import kotlinx.android.synthetic.main.partial_no_data_layout.*

class SourcesFragment : Fragment() {

    private val sourcesAdapter by lazy {
        SourcesAdapter { source ->
            val args = Bundle().apply { putString(NewsListFragment.ARG_SOURCES, source.id) }
            findNavController().navigate(R.id.action_newsDetailsFragment_to_newsListFragment, args)
        }
    }

    private var newsActivity: NewsActivity? = null

    private val viewModel: SourcesViewModel by lazy {
        val factory = SourcesViewModel.Factory(requireActivity().application)
        ViewModelProviders.of(this@SourcesFragment, factory).get(SourcesViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NewsActivity) newsActivity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Making sure that the news information is fetched only when there is no data available.
        // 2. We make sure that we make to call manually when the user explicitly asks for it.
        // 3. This check is helpful during a configuration change.
        viewModel.fetchSources()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSources()
        setupDataReload()
    }

    private fun setupSources() {
        rvSources?.run {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            adapter = sourcesAdapter
        }

        viewModel.obsSources.observe(this@SourcesFragment, Observer {

            it?.let { list ->

                // Checking whether the data has been loaded correctly.
                if (list.isEmpty() && newsActivity?.getNetworkState() == false) {
                    rvSources?.gone()
                    plNoData?.visible()
                } else {
                    rvSources?.visible()
                    plNoData?.gone()
                }

                sourcesAdapter.submitList(list)
            }
        })

        // Observing for failure messages.
        viewModel.obsError.observe(this@SourcesFragment, Observer {
            it?.takeIf { err -> !err.isBlank() }
                ?.let { msg -> toast(msg, Toast.LENGTH_LONG) }
        })
    }

    private fun setupDataReload() {
        // Refreshing the data when swipe-to-refresh is performed.
        srlRefreshContent?.setOnRefreshListener {
            fetchDataOnNetwork()
        }

        // Refreshing the data when retry button is clicked.
        btnNoDataRetry?.setOnClickListener {
            fetchDataOnNetwork()
        }

        viewModel.obsProgress.observe(this@SourcesFragment, Observer {
            it?.let { inProgress ->
                if (inProgress) {
                    srlRefreshContent?.inProgress()
                    btnNoDataRetry?.isEnabled = false
                } else {
                    srlRefreshContent.progressComplete()
                    btnNoDataRetry?.isEnabled = true
                }
            }
        })
    }

    /**
     * Fetching the data when there is internet connection.
     */
    private fun fetchDataOnNetwork() {
        // Making sure that the latest data is fetched only when
        // the internet connection is available.
        if (newsActivity?.getNetworkState() == true) {
            viewModel.fetchSources()
        } else {
            srlRefreshContent?.progressComplete()
            newsActivity?.networkSnack?.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SourcesFragment()
    }
}